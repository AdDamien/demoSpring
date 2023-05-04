package edu.dadam.demo.dao;

import edu.dadam.demo.model.ImageDto;
import edu.dadam.demo.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurDao extends JpaRepository <Utilisateur,Integer> {
    Utilisateur findByPrenom(String prenom);
    //Utilisateur findByNom(String nom);
    Optional<Utilisateur> findByEmail(String email);

    //Requete personnaliser en HQL Hibernate Query Language
    @Query("From Utilisateur U JOIN U.pays P WHERE P.nom='Allemagne'")
    List<Utilisateur> findUtilisateurAllemand();

    @Query("From Utilisateur U JOIN U.pays P WHERE P.nom= ?1")
    List<Utilisateur> findUtilisateurSelonPays(String pays);

    /*@Query("Select new ImageDto(U.id , U.ImageProfil)FROM Utilisateur U")
    List<ImageDto> testBidon();*/
}
