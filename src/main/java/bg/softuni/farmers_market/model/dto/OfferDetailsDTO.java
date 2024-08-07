package bg.softuni.farmers_market.model.dto;

import bg.softuni.farmers_market.model.entity.PictureEntity;
import bg.softuni.farmers_market.model.entity.ProductTypeEntity;
import bg.softuni.farmers_market.model.enums.ProductTypeEnum;

import java.util.ArrayList;
import java.util.List;

public class OfferDetailsDTO {
    private Long id;
    private ProductTypeEnum productType;
    private String description;
    private String name;
    private List<String> pictures;
    private Long authorId;
    private String authorFullName;

    public OfferDetailsDTO() {
        this.pictures = new ArrayList<>();
    }

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

    public List<String> getPictures() {
        return pictures;
    }

    public OfferDetailsDTO setPictures(List<String> pictures) {
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

    public Long getAuthorId() {
        return authorId;
    }

    public OfferDetailsDTO setAuthorId(Long authorId) {
        this.authorId = authorId;
        return this;
    }


}
