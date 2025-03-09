package com.example.crud.application.ports.input;

import com.example.crud.domain.model.ListeDeTache;
import com.example.crud.domain.model.EtatTache;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ListeDeTacheUseCasePort {
    ListeDeTache createListeDeTache(String nom, LocalDate date, EtatTache etat, Long idUser, Long idTache);

    Optional<ListeDeTache> getListeDeTache(Long id);

    List<ListeDeTache> getAllListeDeTaches();

    ListeDeTache updateListeDeTache(Long id, String nom, LocalDate date, EtatTache etat);

    void deleteListeDeTache(Long id);
}