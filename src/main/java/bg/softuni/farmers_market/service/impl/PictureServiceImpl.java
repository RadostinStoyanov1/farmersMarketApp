package bg.softuni.farmers_market.service.impl;

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
                .findByOffer_Id(id)
                .stream()
                .map(p -> p.getUrl())
                .toList();
    }
}
