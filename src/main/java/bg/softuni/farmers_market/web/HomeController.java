package bg.softuni.farmers_market.web;

import bg.softuni.farmers_market.model.user.FarmersUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index(@AuthenticationPrincipal UserDetails userDetails,
                        Model model) {
        if (userDetails instanceof FarmersUserDetails farmersUserDetails) {
            model.addAttribute("welcomeMessage", ", " + farmersUserDetails.getFullName());
        } else {
            model.addAttribute("welcomeMessage", "");
        }

        return "index";
    }
    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/advices")
    public String advices() {
        return "advices";
    }

    @GetMapping("/news")
    public String news() {
        return "news";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }
}
