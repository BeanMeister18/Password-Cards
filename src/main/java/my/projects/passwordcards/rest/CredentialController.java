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
    private CredentialService credentialService;

    @RequestMapping("/")
    public String viewHomePage(Model model) {
        return "index";
    }


    @RequestMapping("/new")
    public String showNewCredentialPage(Model model) {
        Credential credential = new Credential();
        model.addAttribute("credential", credential);
        // display 'newCredential' html page
        return "newCredential";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveCredential(@ModelAttribute("credential") Credential credential) {
        credentialService.save(credential);
        // go back to the main page - index.html
        return "redirect:/";
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String getCredential(@ModelAttribute("credential") Credential credential) {
        credentialService.get(1);
        return "index";
//        return "redirect:/";
    }
}
