package edu.dadam.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import edu.dadam.demo.view.VueEntreprise;
import edu.dadam.demo.view.VueUtilisateur;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity // pour permettre de faire comprendre la liaison entre la classe et la base de donnée.
@Getter // permet de créer les getters avec lombok
@Setter// permet de créer les setters avec lombok
@EntityListeners(AuditingEntityListener.class)
// @Table(name="utilisateur") a mettre si la class correspond pas a une table exemple class user , etc utile pour les langues
// click droit refractor la class.

@CrossOrigin
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({VueUtilisateur.class,VueEntreprise.class})
    private Integer id;

    @JsonView({VueUtilisateur.class, VueEntreprise.class})
    @Column(length = 80, nullable = false)
    private String prenom;

    // @Column (name = "nom")
    @JsonView({VueUtilisateur.class,VueEntreprise.class})
    private String nom;

    @JsonView({VueUtilisateur.class,VueEntreprise.class})
    private int age;

    @JsonView({VueUtilisateur.class,VueEntreprise.class})
    private String email;

    @JsonView(VueUtilisateur.class)
    private String nomImageProfil;


    private String motDePasse;

    @JsonView({VueUtilisateur.class,VueEntreprise.class})
    @ManyToOne
    private Role role;


    @JsonView(VueUtilisateur.class)
    @ManyToOne
    private Pays pays ;


    @ManyToMany
    @JsonView(VueUtilisateur.class)
    @JoinTable(name="recherche_emploi_utilisateur",inverseJoinColumns = @JoinColumn(name="emploi_id"))
    private Set<Emploi> emploisRecherche = new HashSet<>();

    @ManyToOne
    @JsonView(VueUtilisateur.class)
    private Entreprise entreprise;

    @CreationTimestamp
    @JsonView(VueUtilisateur.class)
    private LocalDate createdAt;

    @UpdateTimestamp
    @JsonView(VueUtilisateur.class)
    private LocalDateTime updatedAt;

}
