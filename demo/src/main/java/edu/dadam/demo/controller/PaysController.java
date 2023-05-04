package edu.dadam.demo.controller;

import edu.dadam.demo.dao.PaysDao;
import edu.dadam.demo.model.Pays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class PaysController {

    @Autowired
    private PaysDao paysDao;

    /*public Pays getPays(){
        Pays pays = new Pays();
        pays.setId(42);
        pays.setNom("Bansept");
        pays.setPrenom("Franck");

        return pays;
    }*/
    @GetMapping("/liste-pays")
    public List<Pays> getPays() {
        List<Pays> ListePays = paysDao.findAll();
        return ListePays;
    }

    @GetMapping("/pays/{id}")
    public ResponseEntity<Pays> getPaysNom(@PathVariable int id) {
        Optional<Pays> optional = paysDao.findById(id); // s'il ne trouve rien il retournera cela.
        if (optional.isPresent()) {
            return new ResponseEntity<Pays>(optional.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /*public ArrayList<Pays> getPays(){
      /* Pays pays = new Pays();
        pays.setId(42);
        pays.setNom("Bansept");
        pays.setPrenom("Franck");

        ArrayList<Pays> listepays = new ArrayList<>();
        listepays.add(pays);*/

    /*@GetMapping("/pays-nom")
    public Pays getPaysNom() {
        Pays pays = paysDao.findByNom("Kevin");
        return pays;
    }*/

    /*@PostMapping("/pays")
    public boolean ajoutPays(@RequestBody Pays pays) {

    }*/

    /*@PostMapping("/pays")
    public boolean ajoutPays() {
        Pays nouvelPays = new Pays();
        nouvelPays.setId(3);
        nouvelPays.setPrenom("Jack");
        nouvelPays.setNom("SPARROW");
        paysDao.save(nouvelPays);
        return true;*/


    @PostMapping("/pays")
    public ResponseEntity<Pays> ajoutPays(@RequestBody Pays nouvelPays) {
        // si l'pays fournit possède un id
        if (nouvelPays.getId() != null) {
            Optional<Pays> optional = paysDao.findById(nouvelPays.getId()); // s'il ne trouve rien il retournera cela.
            if (optional.isPresent()) {
                paysDao.save(nouvelPays);
                return new ResponseEntity<Pays>(optional.get(), HttpStatus.OK);
            }
            // si il y a une tentative d'insertion d'un pays avec un id qui n'existait pas
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        paysDao.save(nouvelPays);
        return new ResponseEntity<>(nouvelPays, HttpStatus.CREATED);

    }


    @DeleteMapping("admin/pays/{id}")
    public ResponseEntity<Pays> supprimerPays(@PathVariable int id) {

        Optional<Pays> paysASupprimer = paysDao.findById(id);

        if (paysASupprimer.isPresent()) {
            paysDao.deleteById(id);
            return new ResponseEntity<>(paysASupprimer.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
