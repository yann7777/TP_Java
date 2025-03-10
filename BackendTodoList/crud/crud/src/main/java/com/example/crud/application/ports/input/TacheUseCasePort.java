package com.example.crud.application.ports.input;

import com.example.crud.domain.model.EtatTache;
import com.example.crud.domain.model.Tache;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TacheUseCasePort {
    Tache createTache(String nom, LocalDate date, EtatTache etat, Long idUser);

    Optional<Tache> getTache(Long id);

    List<Tache> getAllTaches();

    Tache updateTache(Long id, String nom, LocalDate date, EtatTache etat);

    void deleteTache(Long id);
}