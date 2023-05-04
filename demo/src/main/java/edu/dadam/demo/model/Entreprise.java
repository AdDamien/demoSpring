package edu.dadam.demo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import edu.dadam.demo.view.VueEntreprise;
import edu.dadam.demo.view.VueUtilisateur;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity // pour faire comprendre qu'il faut créer une table avec ces paramètres dans la bdd
@Getter
@Setter

public class Entreprise {

    @JsonView({VueUtilisateur.class,VueEntreprise.class})
    @Id // id de javax persistence API
    @GeneratedValue(strategy = GenerationType.IDENTITY) // par rapport a l'auto incrémentation
    private Integer id;
    @JsonView(VueUtilisateur.class)
    private String nom;

@OneToMany(mappedBy = "entreprise")
@JsonIgnore // permet d'ignorer une boucle infini en cas de multiple liste
@JsonView(VueEntreprise.class)
    private Set<Utilisateur> listeEmploye = new HashSet<>();


}
