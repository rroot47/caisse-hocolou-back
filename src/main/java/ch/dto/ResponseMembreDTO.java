package ch.dto;

import ch.models.Adherant;

import java.util.List;

public class ResponseMembreDTO {

    private Long id;
    private String nom;
    private String prenom;
    private int telephone;
    private String domicile;
    private  double montantAdhesion;
    //private Adherant[] adherant;
    //private Set<Adherant> adherant;
    private List<Adherant> adherant;
    private double montantTotals;
}
