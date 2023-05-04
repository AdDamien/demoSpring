package edu.dadam.demo.model;

import java.io.Serializable;

public class ImageDto implements Serializable {
    public ImageDto(int idUtilisateur,String ImageProfil){
        this.idUtilisateur = idUtilisateur;
        this.imageProfil = imageProfil;
    }
    private int idUtilisateur;

    private String imageProfil;
}
