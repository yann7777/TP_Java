package com.example.crud.application.ports.output;

import java.util.List;
import java.util.Optional;

import com.example.crud.domain.model.ListeDeTache;

public interface ListeDeTacheRepositoryPort {
    ListeDeTache save(ListeDeTache listeDeTache);

    Optional<ListeDeTache> findById(Long id);

    List<ListeDeTache> findAll();

    void deleteById(Long id);
}
