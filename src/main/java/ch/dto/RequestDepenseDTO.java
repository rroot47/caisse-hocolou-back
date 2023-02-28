package ch.dto;

import ch.enums.TypeDepense;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestDepenseDTO {
    private TypeDepense typeDepense;
    private String description;
    private String nom;
    private String prenom;
    private String date;
    private double somme;
}
