package ch.repository;

import ch.models.Signing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SigningRepo extends JpaRepository<Signing, Long> {
}
