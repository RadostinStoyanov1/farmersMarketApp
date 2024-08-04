package bg.softuni.farmers_market.service.impl;

import bg.softuni.farmers_market.model.dto.OfferDetailsDTO;
import bg.softuni.farmers_market.model.entity.PictureEntity;
import bg.softuni.farmers_market.repository.PictureRepository;
import bg.softuni.farmers_market.service.PictureService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {
    private final PictureRepository pictureRepository;

    public PictureServiceImpl(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    @Override
    public List<String> getPictureUrlsByOfferId(Long id) {
        return pictureRepository
                .findAllByOfferId(id)
                .stream()
                .map(PictureEntity::getUrl)
                .toList();
    }

    public void create(OfferDetailsDTO offerDetailsDTO, String picturePath) {
        PictureEntity pictureEntity = new PictureEntity();

        pictureEntity.setTitle(offerDetailsDTO.getName());
        pictureEntity.setOfferId(offerDetailsDTO.getId());
        pictureEntity.setUrl(picturePath);

        pictureRepository.saveAndFlush(pictureEntity);

    }

    public void deletePicturesByOfferId(Long offerId) {
        pictureRepository.deleteByOfferId(offerId);
    }
}
