package bg.softuni.farmers_market.model.dto;

import bg.softuni.farmers_market.model.enums.ProductTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AddOfferDTO {
    @NotBlank
    private ProductTypeEnum productType;
    private String description;
    @NotBlank
    private String name;
    @NotBlank
    private Long author;

    public ProductTypeEnum getProductType() {
        return productType;
    }

    public AddOfferDTO setProductType(ProductTypeEnum productType) {
        this.productType = productType;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public AddOfferDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getName() {
        return name;
    }

    public AddOfferDTO setName(String name) {
        this.name = name;
        return this;
    }

    public Long getAuthor() {
        return author;
    }

    public AddOfferDTO setAuthor(Long author) {
        this.author = author;
        return this;
    }
}
