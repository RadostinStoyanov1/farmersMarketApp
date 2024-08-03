package bg.softuni.farmers_market.web;

import bg.softuni.farmers_market.model.dto.OfferDetailsDTO;
import bg.softuni.farmers_market.model.dto.UploadPictureDTO;
import bg.softuni.farmers_market.model.enums.ProductTypeEnum;
import bg.softuni.farmers_market.service.OfferService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/offers")
public class OffersController {
    private final OfferService offerService;

    public OffersController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping("/all")
    public String getAllOffers(Model model) {

        model.addAttribute("allOffers", offerService.getAllOffersSummary());
        return "offers";
    }

    @GetMapping("/all/{type}")
    public String getAllOffersByType(@PathVariable("type") String type, Model model) {

        model.addAttribute("allOffers", offerService.getAllOffersByType(type));
        return "offers";
    }
}
