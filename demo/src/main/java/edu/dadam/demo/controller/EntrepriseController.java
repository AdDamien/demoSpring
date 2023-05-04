package edu.dadam.demo.controller;

import edu.dadam.demo.dao.EntrepriseDao;
import edu.dadam.demo.model.Entreprise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // si version ultimate pour dire qu'il s'agit d'un controller
public class EntrepriseController {

    @Autowired
    private EntrepriseDao entrepriseDao;

    @GetMapping("/entreprises")
    public List<Entreprise> ListeEntreprise(){
        List<Entreprise> entreprises = entrepriseDao.findAll();
        return entreprises;
    }
    @GetMapping("/entreprises/{id}")
    public Entreprise getEntreprise(@PathVariable int id){
        Entreprise entreprise = entrepriseDao.findById(id).orElse(null);
        return entreprise;
    }
    @DeleteMapping("admin/entreprises/{id}")
    public boolean supprimerEntreprise(@PathVariable int id){
        entrepriseDao.deleteById(id);
        return true;
    }
    @PostMapping("admin/entreprises")
    public Entreprise enregistrerEntreprise(@RequestBody Entreprise entrepriseAEnregistrer){
        entrepriseDao.save(entrepriseAEnregistrer);

        return entrepriseAEnregistrer;
    }
}
