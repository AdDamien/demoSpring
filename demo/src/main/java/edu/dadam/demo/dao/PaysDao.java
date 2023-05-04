package edu.dadam.demo.dao;

import edu.dadam.demo.model.Pays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaysDao extends JpaRepository <Pays,Integer> {

}
