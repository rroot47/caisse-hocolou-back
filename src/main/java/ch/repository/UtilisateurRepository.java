package ch.repository;

import ch.models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    public Optional<Utilisateur> findUtilisateurByEmail(String email);

    @Query("select u.roles from Utilisateur u where u.email= :email")
    Collection<Utilisateur> getRoleToUser(@Param("email") String email);

    @Query("select u from Utilisateur u where u.id = :id")
    List<Utilisateur> getUserAndRoleName(Long id);

    @Query("SELECT u FROM Utilisateur u WHERE u.verificationCode = ?1")
    public Utilisateur findByVerificationCode(String code);
}
