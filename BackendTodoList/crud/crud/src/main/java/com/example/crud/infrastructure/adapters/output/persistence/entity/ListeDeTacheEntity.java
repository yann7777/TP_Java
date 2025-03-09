package com.example.crud.infrastructure.adapters.output.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

import com.example.crud.domain.model.EtatTache;

@Entity
@Table(name = "listedetache")
public class ListeDeTacheEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private EtatTache etat;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "id_tache", nullable = false) // Assurez-vous que nullable = false
    private TacheEntity tache;

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

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public TacheEntity getTache() {
        return tache;
    }

    public void setTache(TacheEntity tache) {
        this.tache = tache;
    }
}