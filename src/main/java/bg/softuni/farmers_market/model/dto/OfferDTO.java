package bg.softuni.farmers_market.model.dto;

import bg.softuni.farmers_market.model.enums.ProductTypeEnum;

public class OfferDTO {
    private Long id;
    private ProductTypeEnum productType;
    private String description;
    private String name;
    private Long author;
    private Long likes;

    public Long getId() {
        return id;
    }

    public OfferDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public ProductTypeEnum getProductType() {
        return productType;
    }

    public OfferDTO setProductType(ProductTypeEnum productType) {
        this.productType = productType;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public OfferDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getName() {
        return name;
    }

    public OfferDTO setName(String name) {
        this.name = name;
        return this;
    }

    public Long getAuthor() {
        return author;
    }

    public OfferDTO setAuthor(Long author) {
        this.author = author;
        return this;
    }

    public Long getLikes() {
        return likes;
    }

    public OfferDTO setLikes(Long likes) {
        this.likes = likes;
        return this;
    }
}
