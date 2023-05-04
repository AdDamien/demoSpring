package edu.dadam.demo.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.dadam.demo.dao.UtilisateurDao;
import edu.dadam.demo.model.ImageDto;
import edu.dadam.demo.model.Role;
import edu.dadam.demo.model.Utilisateur;
import edu.dadam.demo.security.JwtUtils;
import edu.dadam.demo.services.FichierService;
import edu.dadam.demo.view.VueEntreprise;
import edu.dadam.demo.view.VueUtilisateur;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationImportListener;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.*;

@RestController
@CrossOrigin
public class UtilisateurController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    private UtilisateurDao utilisateurDao;

    @Autowired
    private FichierService fichierService;

    /*public Utilisateur getUtilisateur(){
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(42);
        utilisateur.setNom("Bansept");
        utilisateur.setPrenom("Franck");

        return utilisateur;
    }*/
    @GetMapping("/utilisateur")
    @JsonView(VueUtilisateur.class) // pour filtrer la vue en fonction du chemin du getmapping exemple ici /utilisateur
    public List<Utilisateur> getUtilisateur() {
        List<Utilisateur> ListeUtilisateur = utilisateurDao.findAll();
        return ListeUtilisateur;
    }

    @GetMapping("/utilisateur/{id}")
    @JsonView(VueUtilisateur.class)
    public ResponseEntity<Utilisateur> getUtilisateurPrenom(@PathVariable int id) {


        Optional<Utilisateur> optional = utilisateurDao.findById(id); // s'il ne trouve rien il retournera cela.
        if (optional.isPresent()) {
            return new ResponseEntity<Utilisateur>(optional.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/utilisateur-allemand")
    @JsonView(VueUtilisateur.class)
    public List<Utilisateur> getUtilisateurAllemand() {
        return utilisateurDao.findUtilisateurAllemand();

    }

    @GetMapping("/utilisateur-par-pays/{nomPays}")
    @JsonView(VueUtilisateur.class)
    public List<Utilisateur> getUtilisateurParPays(@PathVariable String nomPays) {
        return utilisateurDao.findUtilisateurSelonPays(nomPays);
    }

    /*@GetMapping("/utilisateur-bidon")
    @JsonView(VueUtilisateur.class)
    public List<ImageDto> getUtilisateurBidon() {
        return utilisateurDao.testBidon();
    }*/


    /*public ArrayList<Utilisateur> getUtilisateur(){
      /* Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(42);
        utilisateur.setNom("Bansept");
        utilisateur.setPrenom("Franck");

        ArrayList<Utilisateur> listeutilisateur = new ArrayList<>();
        listeutilisateur.add(utilisateur);*/

    @GetMapping("/utilisateur-prenom")
    public Utilisateur getUtilisateurPrenom() {
        Utilisateur utilisateur = utilisateurDao.findByPrenom("Kevin");
        return utilisateur;
    }



    /*@PostMapping("/utilisateur")
    public boolean ajoutUtilisateur(@RequestBody Utilisateur utilisateur) {

    }*/

    /*@PostMapping("/utilisateur")
    public boolean ajoutUtilisateur() {
        Utilisateur nouvelUtilisateur = new Utilisateur();
        nouvelUtilisateur.setId(3);
        nouvelUtilisateur.setPrenom("Jack");
        nouvelUtilisateur.setNom("SPARROW");
        utilisateurDao.save(nouvelUtilisateur);
        return true;*/
    @GetMapping("/image-profil/{idUtilisateur}")
    public ResponseEntity<byte[]> getImageProfil(@PathVariable int idUtilisateur) {
        Optional<Utilisateur> optional = utilisateurDao.findById(idUtilisateur);
        if (optional.isPresent()) {
            String nomImage = optional.get().getNomImageProfil();
            try {
                byte[] image = fichierService.getImageByName(nomImage);
                HttpHeaders enTete = new HttpHeaders();
                String mimeType = Files.probeContentType(new File(nomImage).toPath());
                enTete.setContentType(MediaType.valueOf(mimeType));
                return new ResponseEntity<>(image, enTete, HttpStatus.OK);
            } catch (FileNotFoundException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } catch (IOException e) {
                System.out.println("Le test du mimeType a echoue");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



    @PostMapping("/admin/utilisateur")
    @JsonView(VueUtilisateur.class)
    public ResponseEntity<Utilisateur> ajoutUtilisateur(
            @RequestPart("utilisateur") Utilisateur nouvelUtilisateur,
            @Nullable @RequestParam("fichier") MultipartFile fichier) {
        // si l'utilisateur fournit possède un id
        if (nouvelUtilisateur.getId() != null) {
            Optional<Utilisateur> optional = utilisateurDao.findById(nouvelUtilisateur.getId()); // s'il ne trouve rien il retournera cela.
            if (optional.isPresent()) {

                Utilisateur userToUpdate = optional.get();
                userToUpdate.setNom(nouvelUtilisateur.getNom());
                userToUpdate.setPrenom(nouvelUtilisateur.getPrenom());
                userToUpdate.setEmail(nouvelUtilisateur.getEmail());
                userToUpdate.setPays(nouvelUtilisateur.getPays());


                utilisateurDao.save(userToUpdate);
                if (fichier != null) {
                    try {
                        fichierService.transfertVersDossierUpload(fichier, "image-profil");
                    } catch (IOException e) {
                        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }
                return new ResponseEntity<Utilisateur>(optional.get(), HttpStatus.OK);
            }
            // si il y a une tentative d'insertion d'un utilisateur avec un id qui n'existait pas
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Role role = new Role();
        role.setId(2);
        nouvelUtilisateur.setRole(role);
        String passwordCrypter = passwordEncoder.encode("root");
        nouvelUtilisateur.setMotDePasse(passwordCrypter);


        if (fichier != null) {
            try {
                String nomImage = UUID.randomUUID() + "_" + fichier.getOriginalFilename();
                nouvelUtilisateur.setNomImageProfil(nomImage);

                fichierService.transfertVersDossierUpload(fichier, nomImage);
            } catch (IOException e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        utilisateurDao.save(nouvelUtilisateur);
        return new ResponseEntity<>(nouvelUtilisateur, HttpStatus.CREATED);
    }


    @DeleteMapping("/admin/utilisateur/{id}")
    public ResponseEntity<Utilisateur> supprimerUtilisateur(@PathVariable int id) {

        Optional<Utilisateur> utilisateurASupprimer = utilisateurDao.findById(id);

        if (utilisateurASupprimer.isPresent()) {
            utilisateurDao.deleteById(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



        @GetMapping("/profil")
        @JsonView(VueUtilisateur.class)
        public ResponseEntity<Utilisateur> getProfil (@RequestHeader("Authorization") String bearer) {
            String jwt = bearer.substring(7);
            Claims donnees = jwtUtils.getData(jwt);
            Optional<Utilisateur> utilisateur = utilisateurDao.findByEmail(donnees.getSubject());
            if (utilisateur.isPresent()) {
                return new ResponseEntity<>(utilisateur.get(), HttpStatus.OK);

            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }




