package bg.softuni.farmers_market.web;

import bg.softuni.farmers_market.model.dto.AddOfferDTO;
import bg.softuni.farmers_market.model.enums.ProductTypeEnum;
import bg.softuni.farmers_market.service.ProductTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Arrays;

@Controller
public class ProductsController {

    private final ProductTypeService productTypeService;

    public ProductsController(ProductTypeService productTypeService) {
        this.productTypeService = productTypeService;
    }

    @ModelAttribute("allProductTypes")
    public String[] allProductTypes() {
        return Arrays.stream(productTypeService
                .getAllProductTypes())
                .map(Enum::toString)
                .toArray(String[]::new);
    }

    @GetMapping("/products")
    public String getProducts(Model model) {

        return "products";
    }
}
