package my.projects.passwordcards.rest;

import my.projects.passwordcards.model.Credential;
import my.projects.passwordcards.service.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class CredentialController {

    @Autowired
    private CredentialService credentialService;

    @RequestMapping("/")
    public String viewHomePage(Model model) {
        List<Credential> listOfCredentials = credentialService.listAll();
        model.addAttribute("listCredential", listOfCredentials);

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
        if(null == credential || !StringUtils.hasLength(credential.getWebsite())) {
            throw new IllegalArgumentException("Credential object cannot be null");
        }
        credentialService.save(credential);
        // go back to the main page - index.html
        return "redirect:/";
    }


    @RequestMapping("/edit/{id}")
    public ModelAndView showEditCredentialPage(@PathVariable(name = "id") int id) {
        if(0 >= id) {
            throw new IllegalArgumentException("Illegal ID value: " + id);
        }
        ModelAndView mav = new ModelAndView("editCredential");
        // NoSuchElementException will be thrown and handled by ErrorHandler class
        Credential cr = credentialService.get(id);

        mav.addObject("credential", cr);

        return mav;
    }

    @RequestMapping("/delete/{id}")
    public String deleteCredential(@PathVariable(name = "id") int id) {
        if(0 >= id) {
            throw new IllegalArgumentException("Illegal ID value: " + id);
        }
        try {
            credentialService.delete(id);
        } catch (EmptyResultDataAccessException e) {
            System.err.println("Element not found. ID = " + id);
            throw e;
        }
        // all other exceptions will be handles by ErrorHandler class
        return "redirect:/";
    }
}
