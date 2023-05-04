package edu.dadam.demo.dao;

import edu.dadam.demo.model.Emploi;
import edu.dadam.demo.model.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntrepriseDao extends JpaRepository <Entreprise,Integer> {

}
