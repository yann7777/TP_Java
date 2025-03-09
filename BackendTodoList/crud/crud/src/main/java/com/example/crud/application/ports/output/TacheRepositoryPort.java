package com.example.crud.application.ports.output;

import java.util.List;
import java.util.Optional;

import com.example.crud.domain.model.Tache;

public interface TacheRepositoryPort {
    Tache save(Tache tache);

    Optional<Tache> findById(Long id);

    List<Tache> findAll();

    void deleteById(Long id);
}



