package bg.softuni.farmers_market.service;

import java.util.List;

public interface PictureService {
    public List<String> getPictureUrlsByOfferId(Long id);
}
