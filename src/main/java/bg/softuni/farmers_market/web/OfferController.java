package bg.softuni.farmers_market.web;

import bg.softuni.farmers_market.model.dto.AddOfferDTO;
import bg.softuni.farmers_market.model.dto.OfferDetailsDTO;
import bg.softuni.farmers_market.model.dto.UploadPictureDTO;
import bg.softuni.farmers_market.model.enums.ProductTypeEnum;
import bg.softuni.farmers_market.service.OfferService;
import bg.softuni.farmers_market.service.ProductTypeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/offers")
public class OfferController {
    private final OfferService offerService;
    private final ProductTypeService productTypeService;

    public OfferController(OfferService offerService, ProductTypeService productTypeService) {
        this.offerService = offerService;
        this.productTypeService = productTypeService;
    }

    @ModelAttribute("allProductTypes")
    public ProductTypeEnum[] allProductTypes() {
        return productTypeService.getAllProductTypes();
    }

    @GetMapping("/add")
    public String newOffer(Model model) {

        if (!model.containsAttribute("addOfferDTO")) {
            model.addAttribute("addOfferDTO", new AddOfferDTO());
        }

        return "offer-add";
    }

    @PostMapping("/add")
    public String addOffer(
            @Valid AddOfferDTO addOfferDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addOfferDTO", addOfferDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addOfferDTO", bindingResult);

            return "redirect:/offers/add";
        }

        offerService.createOffer(addOfferDTO);

        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String offerDetails(@PathVariable Long id, Model model) {
        OfferDetailsDTO currentOffer = offerService.getOfferDetails(id);
        boolean canDelete = offerService.canDelete(currentOffer);
        boolean canLike = offerService.canUpload(currentOffer);

        model.addAttribute("offerDetails", currentOffer);
        model.addAttribute("uploadPictureDTO", new UploadPictureDTO());
        model.addAttribute("canLike", canLike);
        model.addAttribute("canDelete", canDelete);
        return "details";
    }

    @PostMapping("/upload-picture")
    public String uploadPicture(@ModelAttribute @Valid UploadPictureDTO uploadPictureDTO) {;
        offerService.uploadPicture(uploadPictureDTO);

        return ("redirect:/offers/all");
    }

}
