package bg.softuni.farmers_market.service.impl;

import bg.softuni.farmers_market.model.dto.AddOfferDTO;
import bg.softuni.farmers_market.model.dto.OfferDTO;
import bg.softuni.farmers_market.model.dto.OfferDetailsDTO;
import bg.softuni.farmers_market.model.dto.OfferSummaryDTO;
import bg.softuni.farmers_market.model.entity.UserEntity;
import bg.softuni.farmers_market.service.OfferService;
import bg.softuni.farmers_market.service.PictureService;
import bg.softuni.farmers_market.service.UserService;
import bg.softuni.farmers_market.service.helper.UserHelperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {
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
        List<OfferDTO> incomingOffers = offerRestClient
                .get()
                .uri("/offers")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});

        List<OfferSummaryDTO> allOffers = incomingOffers
                .stream()
                .map(this::convertOfferDTOToOfferSummaryDTO)
                .toList();

        return allOffers;
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
        offerSummaryDTO.setProductType(offerDTO.getProductType());
        offerSummaryDTO.setAuthorName(getFullName(author));
        offerSummaryDTO.setLikes(offerDTO.getLikes());
        return offerSummaryDTO;
    }

    @Override
    public OfferDetailsDTO getOfferDetails(Long id) {
        return offerRestClient
                .get()
                .uri("/offers/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(OfferDetailsDTO.class);

    }

    @Override
    public void deleteOffer(Long id) {

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
}
