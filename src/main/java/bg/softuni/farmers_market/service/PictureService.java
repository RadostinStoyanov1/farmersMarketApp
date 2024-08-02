package bg.softuni.farmers_market.service;

import bg.softuni.farmers_market.model.dto.OfferDetailsDTO;

import java.util.List;

public interface PictureService {
    public List<String> getPictureUrlsByOfferId(Long id);
    public void create(OfferDetailsDTO offerDetailsDTO, String picturePath);
}
