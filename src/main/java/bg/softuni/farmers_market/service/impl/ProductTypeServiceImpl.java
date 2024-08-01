package bg.softuni.farmers_market.service.impl;

import bg.softuni.farmers_market.model.entity.ProductTypeEntity;
import bg.softuni.farmers_market.model.enums.ProductTypeEnum;
import bg.softuni.farmers_market.repository.ProductTypeRepository;
import bg.softuni.farmers_market.service.ProductTypeService;
import org.springframework.stereotype.Service;

@Service
public class ProductTypeServiceImpl implements ProductTypeService {

    private final ProductTypeRepository productTypeRepository;

    public ProductTypeServiceImpl(ProductTypeRepository productTypeRepository) {
        this.productTypeRepository = productTypeRepository;
    }

    @Override
    public ProductTypeEnum[] getAllProductTypes() {
        ProductTypeEnum[] productTypes = productTypeRepository
                .findAll()
                .stream()
                .map(ProductTypeEntity::getProductType)
                .toArray(ProductTypeEnum[]::new);
        return productTypes;
    }
}
