package edu.dadam.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

@GetMapping("/")// faire un lien entre la route et la méthode , ("/") fait le chemin de base
    public String hello(){
        return "Le serveur marche mais y'a rien ici";

    }
}
