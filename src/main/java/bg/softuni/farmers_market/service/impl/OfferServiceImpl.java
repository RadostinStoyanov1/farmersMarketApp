package bg.softuni.farmers_market.service.impl;

import bg.softuni.farmers_market.model.dto.AddOfferDTO;
import bg.softuni.farmers_market.model.dto.OfferDetailsDTO;
import bg.softuni.farmers_market.model.dto.OfferSummaryDTO;
import bg.softuni.farmers_market.service.OfferService;
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

    public OfferServiceImpl(RestClient offerRestClient, UserHelperService userHelperService) {
        this.offerRestClient = offerRestClient;
        this.userHelperService = userHelperService;
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

        return offerRestClient
                .get()
                .uri("/offers")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});

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
}
