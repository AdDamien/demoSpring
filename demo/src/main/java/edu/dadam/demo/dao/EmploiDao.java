package edu.dadam.demo.dao;

import edu.dadam.demo.model.Emploi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmploiDao extends JpaRepository <Emploi,Integer> {

}
