package com.example.kevinfalsblocnote;

public class Note {
    private int id;
    private String titre;
    private String contenu;
    private String dateCreation;

    public Note() {}

    public Note(String titre, String contenu) {
        this.titre = titre;
        this.contenu = contenu;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }
}