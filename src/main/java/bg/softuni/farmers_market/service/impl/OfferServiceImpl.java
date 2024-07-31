package bg.softuni.farmers_market.service.impl;

import bg.softuni.farmers_market.model.dto.AddOfferDTO;
import bg.softuni.farmers_market.model.dto.OfferDetailsDTO;
import bg.softuni.farmers_market.model.dto.OfferSummaryDTO;
import bg.softuni.farmers_market.service.OfferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import java.util.List;

public class OfferServiceImpl implements OfferService {
    private Logger LOGGER = LoggerFactory.getLogger(OfferServiceImpl.class);
    private final RestClient offerRestClient;

    public OfferServiceImpl(RestClient offerRestClient) {
        this.offerRestClient = offerRestClient;
    }

    @Override
    public void createOffer(AddOfferDTO addOfferDTO) {
        LOGGER.info("Creating new offer...");

        offerRestClient
                .post()
                .uri("/offers")
                .body(addOfferDTO)
                .retrieve();
    }

    @Override
    public List<OfferSummaryDTO> getAllOffersSummary() {
        LOGGER.info("Get all offers...");

        return offerRestClient
                .get()
                .uri("http://localhost:8081/offers")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});

    }

    @Override
    public OfferDetailsDTO getOfferDetails(Long id) {
        return offerRestClient
                .get()
                .uri("http://localhost:8081/offers/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(OfferDetailsDTO.class);

    }

    @Override
    public void deleteOffer(Long id) {

    }
}
