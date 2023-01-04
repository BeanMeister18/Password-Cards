package my.projects.passwordcards.rest;

import my.projects.passwordcards.model.Credential;
import my.projects.passwordcards.service.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CredentialController {

    @Autowired
    private CredentialService service;

    @RequestMapping("/")
    public String viewHomePage(Model model) {
        return "index";
    }

    @RequestMapping(value = "/save", method = RequestMethod.GET)
    public String saveCredentail(@ModelAttribute("credential") Credential credential) {
        service.save(credential);
        return "index";
//        return "redirect:/";
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String getCredential(@ModelAttribute("credential") Credential credential) {
        service.get(1);
        return "index";
//        return "redirect:/";
    }
}
