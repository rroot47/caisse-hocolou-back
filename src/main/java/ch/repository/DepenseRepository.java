package ch.repository;

import ch.models.Depense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface DepenseRepository extends JpaRepository<Depense, Long> {
    @Query("select  SUM(d.somme) from Depense d where d.somme NOT IN(0)")
    double montantTotolDepense();
}