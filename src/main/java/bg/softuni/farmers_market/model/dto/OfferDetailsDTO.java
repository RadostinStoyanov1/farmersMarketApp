package bg.softuni.farmers_market.model.dto;

import bg.softuni.farmers_market.model.entity.PictureEntity;
import bg.softuni.farmers_market.model.entity.ProductTypeEntity;
import bg.softuni.farmers_market.model.enums.ProductTypeEnum;

import java.util.List;

public class OfferDetailsDTO {
    private Long id;
    private ProductTypeEnum productType;
    private String description;
    private String name;
    private List<PictureEntity> pictures;
    private String authorFullName;
    private Long likes;

    public Long getId() {
        return id;
    }

    public OfferDetailsDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public ProductTypeEnum getProductType() {
        return productType;
    }

    public OfferDetailsDTO setProductType(ProductTypeEnum productType) {
        this.productType = productType;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public OfferDetailsDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getName() {
        return name;
    }

    public OfferDetailsDTO setName(String name) {
        this.name = name;
        return this;
    }

    public List<PictureEntity> getPictures() {
        return pictures;
    }

    public OfferDetailsDTO setPictures(List<PictureEntity> pictures) {
        this.pictures = pictures;
        return this;
    }

    public String getAuthorFullName() {
        return authorFullName;
    }

    public OfferDetailsDTO setAuthorFullName(String authorFullName) {
        this.authorFullName = authorFullName;
        return this;
    }

    public Long getLikes() {
        return likes;
    }

    public OfferDetailsDTO setLikes(Long likes) {
        this.likes = likes;
        return this;
    }
}
