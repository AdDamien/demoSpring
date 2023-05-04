package edu.dadam.demo.model;

import com.fasterxml.jackson.annotation.JsonView;
import edu.dadam.demo.view.VueUtilisateur;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity // pour permettre de faire comprendre la liaison entre la classe et la base de donnée.
@Getter // permet de créer les getters avec lombok
@Setter// permet de créer les setters avec lombok
@EntityListeners(AuditingEntityListener.class)
// @Table(name="utilisateur") a mettre si la class correspond pas a une table exemple class user , etc utile pour les langues
// click droit refractor la class.

public class Emploi {
    @JsonView(VueUtilisateur.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // @Column (name = "nom")
    @JsonView(VueUtilisateur.class)
    private String nom;



}
