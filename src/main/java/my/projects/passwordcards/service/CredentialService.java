package my.projects.passwordcards.service;

import my.projects.passwordcards.model.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CredentialService {

    @Autowired
    private CredentialRepository repo;

    public void save(Credential credential) {
        repo.save(credential);
    }

    public List<Credential> listAll() {
        return repo.findAll();
    }

    public Credential get(long id) {
        return repo.findById(id).get();
    }

    public void delete(long id) { //NEW!!!
        repo.deleteById(id);
    }

}
