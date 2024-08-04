package bg.softuni.farmers_market.service;

import bg.softuni.farmers_market.model.dto.AddOfferDTO;
import bg.softuni.farmers_market.model.dto.OfferDetailsDTO;
import bg.softuni.farmers_market.model.dto.OfferSummaryDTO;
import bg.softuni.farmers_market.model.dto.UploadPictureDTO;
import bg.softuni.farmers_market.model.enums.ProductTypeEnum;

import java.util.List;

public interface OfferService {
    public void createOffer(AddOfferDTO addOfferDTO);
    public List<OfferSummaryDTO> getAllOffersSummary();
    public OfferDetailsDTO getOfferDetails(Long id);
    public void deleteOffer(Long id);
    public void uploadPicture(UploadPictureDTO uploadPictureDTO);
    public boolean canUpload(OfferDetailsDTO currentOffer);

    public boolean canDelete (OfferDetailsDTO currentOffer);

    public List<OfferSummaryDTO> getAllOffersByType(String productType);
    public List<OfferSummaryDTO> getMyOffersSummary();

}
