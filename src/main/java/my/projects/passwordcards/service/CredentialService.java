package my.projects.passwordcards.service;

import my.projects.passwordcards.model.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CredentialService {

    @Autowired
    private CredentialRepository repo;

    public void save(Credential credential) {
        System.err.println("Doing 'save'");
//        repo.save(credential);
    }

    public Credential get(long id) {
        System.err.println("Doing 'get'");
        return null;
//        return repo.findById(id).get();
    }

}
