package my.projects.passwordcards.service;

import my.projects.passwordcards.model.Credential;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredentialRepository extends JpaRepository<Credential, Long> {
}
