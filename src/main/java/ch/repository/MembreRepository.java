package ch.repository;

import ch.dto.AllMemberDTO;
import ch.models.Membre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface MembreRepository extends JpaRepository<Membre, Long> {
    @Query("select  SUM(m.montantTotals) from Membre m where m.montantTotals NOT IN(0)")
    double montantTotol();

    Page<AllMemberDTO> findById(Long id, Pageable page);
}