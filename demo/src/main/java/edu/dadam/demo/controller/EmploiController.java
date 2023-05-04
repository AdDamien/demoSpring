package edu.dadam.demo.controller;

import edu.dadam.demo.dao.EmploiDao;
import edu.dadam.demo.model.Emploi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class EmploiController {

    @Autowired
    private EmploiDao emploiDao;

    /*public Emploi getEmploi(){
        Emploi emploi = new Emploi();
        emploi.setId(42);
        emploi.setNom("Bansept");
        emploi.setPrenom("Franck");

        return emploi;
    }*/
    @GetMapping("/emploi")
    public List<Emploi> getEmploi() {
        List<Emploi> ListeEmploi = emploiDao.findAll();
        return ListeEmploi;
    }

    @GetMapping("/emploi/{id}")
    public ResponseEntity<Emploi> getEmploiNom(@PathVariable int id) {
        Optional<Emploi> optional = emploiDao.findById(id); // s'il ne trouve rien il retournera cela.
        if (optional.isPresent()) {
            return new ResponseEntity<Emploi>(optional.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /*public ArrayList<Emploi> getEmploi(){
      /* Emploi emploi = new Emploi();
        emploi.setId(42);
        emploi.setNom("Bansept");
        emploi.setPrenom("Franck");

        ArrayList<Emploi> listeemploi = new ArrayList<>();
        listeemploi.add(emploi);*/

    /*@GetMapping("/emploi-nom")
    public Emploi getEmploiNom() {
        Emploi emploi = emploiDao.findByNom("Kevin");
        return emploi;
    }*/

    /*@PostMapping("/emploi")
    public boolean ajoutEmploi(@RequestBody Emploi emploi) {

    }*/

    /*@PostMapping("/emploi")
    public boolean ajoutEmploi() {
        Emploi nouvelEmploi = new Emploi();
        nouvelEmploi.setId(3);
        nouvelEmploi.setPrenom("Jack");
        nouvelEmploi.setNom("SPARROW");
        emploiDao.save(nouvelEmploi);
        return true;*/


    @PostMapping("/emploi")
    public ResponseEntity<Emploi> ajoutEmploi(@RequestBody Emploi nouvelEmploi) {
        // si l'emploi fournit possède un id
        if (nouvelEmploi.getId() != null) {
            Optional<Emploi> optional = emploiDao.findById(nouvelEmploi.getId()); // s'il ne trouve rien il retournera cela.
            if (optional.isPresent()) {
                emploiDao.save(nouvelEmploi);
                return new ResponseEntity<Emploi>(optional.get(), HttpStatus.OK);
            }
            // si il y a une tentative d'insertion d'un emploi avec un id qui n'existait pas
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        emploiDao.save(nouvelEmploi);
        return new ResponseEntity<>(nouvelEmploi, HttpStatus.CREATED);

    }


    @DeleteMapping("/emploi/{id}")
    public ResponseEntity<Emploi> supprimerEmploi(@PathVariable int id) {

        Optional<Emploi> emploiASupprimer = emploiDao.findById(id);

        if (emploiASupprimer.isPresent()) {
            emploiDao.deleteById(id);
            return new ResponseEntity<>(emploiASupprimer.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
