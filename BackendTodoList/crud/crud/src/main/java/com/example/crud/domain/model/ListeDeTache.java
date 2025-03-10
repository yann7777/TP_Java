package com.example.crud.domain.model;

import java.time.LocalDate;

public class ListeDeTache {
    private Long id;
    private String nom;
    private LocalDate date;
    private EtatTache etat;
    private Long idUser; // Identifiant de l'utilisateur
    private Long idTache; // Identifiant de la tâche

    // Constructeurs
    public ListeDeTache() {}

    public ListeDeTache(String nom, LocalDate date, EtatTache etat, Long idUser, Long idTache) {
        this.nom = nom;
        this.date = date;
        this.etat = etat;
        this.idUser = idUser;
        this.idTache = idTache;
    }

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public EtatTache getEtat() {
        return etat;
    }

    public void setEtat(EtatTache etat) {
        this.etat = etat;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getIdTache() {
        return idTache;
    }

    public void setIdTache(Long idTache) {
        this.idTache = idTache;
    }
}