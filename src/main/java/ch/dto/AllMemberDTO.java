package ch.dto;

import ch.models.Adherant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllMemberDTO {
    private Long id;
    private String nom;
    private String prenom;
    private int telephone;
    private String domicile;
    private  double montantAdhesion;
    //private Adherant[] adherant;
    //private Set<Adherant> adherant;
    private List<Adherant> adherant;
    private double montantTotals=0;
}
