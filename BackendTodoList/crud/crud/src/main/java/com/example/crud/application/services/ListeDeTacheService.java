package com.example.crud.application.services;

import com.example.crud.application.ports.input.ListeDeTacheUseCasePort;
import com.example.crud.domain.model.ListeDeTache;
import com.example.crud.domain.model.EtatTache;
import com.example.crud.infrastructure.adapters.output.persistence.entity.ListeDeTacheEntity;
import com.example.crud.infrastructure.adapters.output.persistence.mapper.ListeDeTacheMapper;
import com.example.crud.infrastructure.adapters.output.persistence.repository.ListeDeTacheRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ListeDeTacheService implements ListeDeTacheUseCasePort {

    private final ListeDeTacheRepository listeDeTacheRepository;
    private final ListeDeTacheMapper listeDeTacheMapper;

    public ListeDeTacheService(ListeDeTacheRepository listeDeTacheRepository, ListeDeTacheMapper listeDeTacheMapper) {
        this.listeDeTacheRepository = listeDeTacheRepository;
        this.listeDeTacheMapper = listeDeTacheMapper;
    }

    @Override
    @Transactional
    public ListeDeTache createListeDeTache(String nom, LocalDate date, EtatTache etat, Long idUser, Long idTache) {
        ListeDeTache listeDeTache = new ListeDeTache(nom, date, etat, idUser, idTache);
        ListeDeTacheEntity listeDeTacheEntity = listeDeTacheMapper.toEntity(listeDeTache);
        listeDeTacheEntity = listeDeTacheRepository.save(listeDeTacheEntity);
        return listeDeTacheMapper.toDomain(listeDeTacheEntity);
    }

    @Override
    public Optional<ListeDeTache> getListeDeTache(Long id) {
        Optional<ListeDeTacheEntity> listeDeTacheEntity = listeDeTacheRepository.findById(id);
        return listeDeTacheEntity.map(listeDeTacheMapper::toDomain);
    }

    @Override
    public List<ListeDeTache> getAllListeDeTaches() {
        List<ListeDeTacheEntity> listeDeTacheEntities = listeDeTacheRepository.findAll();
        return listeDeTacheEntities.stream()
            .map(listeDeTacheMapper::toDomain)
            .toList();
    }

    @Override
    @Transactional
    public ListeDeTache updateListeDeTache(Long id, String nom, LocalDate date, EtatTache etat) {
        return listeDeTacheRepository.findById(id).map(listeDeTacheEntity -> {
            listeDeTacheEntity.setNom(nom);
            listeDeTacheEntity.setDate(date);
            listeDeTacheEntity.setEtat(etat);
            listeDeTacheEntity = listeDeTacheRepository.save(listeDeTacheEntity);
            return listeDeTacheMapper.toDomain(listeDeTacheEntity);
        }).orElseThrow(() -> new RuntimeException("Liste de tâches non trouvée avec l'ID : " + id));
    }

    @Override
    @Transactional
    public void deleteListeDeTache(Long id) {
        listeDeTacheRepository.deleteById(id);
    }
}