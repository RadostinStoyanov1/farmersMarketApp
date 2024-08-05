package bg.softuni.farmers_market.service.impl;

import bg.softuni.farmers_market.model.dto.*;
import bg.softuni.farmers_market.model.entity.UserEntity;
import bg.softuni.farmers_market.service.OfferService;
import bg.softuni.farmers_market.service.PictureService;
import bg.softuni.farmers_market.service.UserService;
import bg.softuni.farmers_market.service.exception.OfferNotFoundException;
import bg.softuni.farmers_market.service.helper.UserHelperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class OfferServiceImpl implements OfferService {
    private static final String DEFAULT_OFFER_PICTURE_URL = "/images/DefaultOfferPicture.jpg";
    private static final String BASE_IMAGES_PATH = "./src/main/resources/static/images/";
    private final Logger LOGGER = LoggerFactory.getLogger(OfferServiceImpl.class);
    private final RestClient offerRestClient;
    private final UserHelperService userHelperService;
    private final PictureService pictureService;
    private final UserService userService;

    public OfferServiceImpl(RestClient offerRestClient, UserHelperService userHelperService, PictureService pictureService, UserService userService) {
        this.offerRestClient = offerRestClient;
        this.userHelperService = userHelperService;
        this.pictureService = pictureService;
        this.userService = userService;
    }

    @Override
    public void createOffer(AddOfferDTO addOfferDTO) {

        Long userId = userHelperService.getUser().getId();
        addOfferDTO.setAuthor(userId);

        LOGGER.info("Creating new offer...");

        offerRestClient
                .post()
                .uri("/offers") // uri without baseUrl"http://localhost:8081/offers"
                .body(addOfferDTO)
                .retrieve();
    }

    @Override
    public List<OfferSummaryDTO> getAllOffersSummary() {
        LOGGER.info("Get all offers...");
        List<OfferDTO> incomingOffers = getALlOffersAsOfferDTO();

        return incomingOffers
                .stream()
                .map(this::convertOfferDTOToOfferSummaryDTO)
                .toList();
    }

    @Override
    public OfferDetailsDTO getOfferDetails(Long id) {
        OfferDTO offerDTO = offerRestClient
                .get()
                .uri("/offers/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(s -> s.isSameCodeAs(HttpStatusCode.valueOf(404)),
                        (req, resp) -> {
                            throw new OfferNotFoundException("Offer with id: " + id + "was not found", id);
                        })
                .body(OfferDTO.class);

        if (offerDTO == null) {
            throw new OfferNotFoundException("Offer with id: " + id + "was not found", id);
        }

        return convertOfferDTOToOfferDetailsDTO(offerDTO);
    }

    @Override
    public void uploadPicture(UploadPictureDTO uploadPictureDTO) {
        MultipartFile pictureFile = uploadPictureDTO.getPicture();

        OfferDetailsDTO offerDetailsDTO = getOfferDetails(uploadPictureDTO.getId());

        String picturePath = getPicturePath(pictureFile, offerDetailsDTO.getName());

        try {
            File file = new File(BASE_IMAGES_PATH + picturePath);
            file.getParentFile().mkdirs();
            file.createNewFile();

            OutputStream outputStream = new FileOutputStream(file);
            outputStream.write(pictureFile.getBytes());


            pictureService.create(offerDetailsDTO, "/images/" + picturePath);

        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }
    }

    @Override
    public void deleteOffer(Long id) {
        offerRestClient
                .delete()
                .uri("/offers/{id}", id)
                .retrieve();

        pictureService.deletePicturesByOfferId(id);
    }

    public List<OfferSummaryDTO> getAllOffersByType(String productType) {
        return getAllOffersSummary()
                .stream()
                .filter(o -> o.getProductType().equals(productType))
                .toList();
    }

    public List<OfferSummaryDTO> getMyOffersSummary() {
        Long userId = userHelperService.getUser().getId();

        return getALlOffersAsOfferDTO()
                .stream()
                .filter(o -> Objects.equals(o.getAuthor(), userId))
                .map(this::convertOfferDTOToOfferSummaryDTO)
                .toList();
    }

    private OfferSummaryDTO convertOfferDTOToOfferSummaryDTO(OfferDTO offerDTO) {
        List<String> pictureUrls = pictureService.getPictureUrlsByOfferId(offerDTO.getId());

        UserEntity author = userService.getUserById(offerDTO.getAuthor());

        String pictureUrl = "";
        if (pictureUrls.size() > 0) {
            pictureUrl = pictureUrls.get(0);
        } else {
            pictureUrl = "/images/DefaultOfferPicture.jpg";
        }

        OfferSummaryDTO offerSummaryDTO = new OfferSummaryDTO();
        offerSummaryDTO.setId(offerDTO.getId());
        offerSummaryDTO.setPictureUrl(pictureUrl);
        offerSummaryDTO.setName(offerDTO.getName());
        offerSummaryDTO.setProductType(offerDTO.getProductType().toString());
        offerSummaryDTO.setAuthorName(getFullName(author));
        return offerSummaryDTO;
    }

    public String getFullName(UserEntity user) {
        StringBuilder fullName = new StringBuilder();
        if (user.getFirstName() != null) {
            fullName.append(user.getFirstName());
        }
        if (user.getLastName() != null) {
            if (!fullName.isEmpty()) {
                fullName.append(" ");
            }
            fullName.append(user.getLastName());
        }

        return fullName.toString();
    }

    private OfferDetailsDTO convertOfferDTOToOfferDetailsDTO(OfferDTO offerDTO) {
        UserEntity author = userService.getUserById(offerDTO.getAuthor());
        List<String> pictureUrls = pictureService.getPictureUrlsByOfferId(offerDTO.getId());
        OfferDetailsDTO offerDetailsDTO = new OfferDetailsDTO();

        if (pictureUrls.size() < 1) {
            offerDetailsDTO.getPictures().add(DEFAULT_OFFER_PICTURE_URL);
        } else {
            offerDetailsDTO.setPictures(pictureUrls);
        }

        offerDetailsDTO.setId(offerDTO.getId());
        offerDetailsDTO.setName(offerDTO.getName());
        offerDetailsDTO.setProductType(offerDTO.getProductType());
        offerDetailsDTO.setDescription(offerDTO.getDescription());
        offerDetailsDTO.setAuthorFullName(getFullName(author));
        offerDetailsDTO.setAuthorId(offerDTO.getAuthor());
        offerDetailsDTO.setLikes(offerDetailsDTO.getLikes());

        return offerDetailsDTO;
    }

    public boolean canUpload(OfferDetailsDTO currentOffer) {
        long currentUserId = userHelperService.getUser().getId();
        Long authorId = currentOffer.getAuthorId();
        return currentUserId == authorId;
    }

    public boolean canDelete (OfferDetailsDTO currentOffer) {
        if (userHelperService.hasRole("ADMIN")) {
            return true;
        }
        return canUpload(currentOffer); //canLike compares if the loggedUser is not author
    }

    private String getPicturePath(MultipartFile pictureFile, String offerName) {
        String ext = getFileExtension(pictureFile.getOriginalFilename());

        String pathPattern = "%s%s." + ext;

        return String.format(pathPattern,
                transformOfferName(offerName),
                UUID.randomUUID());
    }
    private String transformOfferName(String offerName) {
        return offerName.toLowerCase()
                .replaceAll("\\s+", "_");
    }

    private String getFileExtension(String fileName) {
        String[] splitPictureName = fileName.split("\\.");
        return splitPictureName[splitPictureName.length - 1];
    }

    private List<OfferDTO> getALlOffersAsOfferDTO() {
        return offerRestClient
                .get()
                .uri("/offers")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
    }
}
