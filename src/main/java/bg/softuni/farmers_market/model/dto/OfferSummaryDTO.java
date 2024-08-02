package bg.softuni.farmers_market.model.dto;

import bg.softuni.farmers_market.model.enums.ProductTypeEnum;

public class OfferSummaryDTO {
    private Long id;
    private String name;
    private ProductTypeEnum productType;
    private String authorName;
    private Long likes;
    private String pictureUrl;

    public Long getId() {
        return id;
    }

    public OfferSummaryDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public OfferSummaryDTO setName(String name) {
        this.name = name;
        return this;
    }

    public ProductTypeEnum getProductType() {
        return productType;
    }

    public OfferSummaryDTO setProductType(ProductTypeEnum productType) {
        this.productType = productType;
        return this;
    }

    public String getAuthorName() {
        return authorName;
    }

    public OfferSummaryDTO setAuthorName(String authorName) {
        this.authorName = authorName;
        return this;
    }

    public Long getLikes() {
        return likes;
    }

    public OfferSummaryDTO setLikes(Long likes) {
        this.likes = likes;
        return this;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public OfferSummaryDTO setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
        return this;
    }
}
