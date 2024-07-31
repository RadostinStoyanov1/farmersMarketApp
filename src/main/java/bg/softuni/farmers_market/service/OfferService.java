package bg.softuni.farmers_market.service;

import bg.softuni.farmers_market.model.dto.AddOfferDTO;
import bg.softuni.farmers_market.model.dto.OfferDetailsDTO;
import bg.softuni.farmers_market.model.dto.OfferSummaryDTO;

import java.util.List;

public interface OfferService {
    public void createOffer(AddOfferDTO addOfferDTO);
    public List<OfferSummaryDTO> getAllOffersSummary();
    public OfferDetailsDTO getOfferDetails(Long id);
    public void deleteOffer(Long id);

}
