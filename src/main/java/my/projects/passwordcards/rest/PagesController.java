package my.projects.passwordcards.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PagesController {
    @RequestMapping("/")
    public String viewHomePage(Model model) {
        return "index";
    }
}
