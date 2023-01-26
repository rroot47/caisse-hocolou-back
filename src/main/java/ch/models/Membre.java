package ch.models;

import javax.persistence.*;

import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@TypeDef(name = "json", typeClass = JsonType.class)
public class Membre  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    @Column(unique = true)
    private int telephone;
    private String domicile;
    private  double montantAdhesion;
    @Type(type = "json")
    @Column(columnDefinition = "jsonb")
    //private Adherant[] adherant;
    private Set<Adherant> adherant;
}