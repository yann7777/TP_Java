package com.example.crud.application.services;

import com.example.crud.application.ports.input.TacheUseCasePort;
import com.example.crud.domain.model.EtatTache;
import com.example.crud.domain.model.Tache;
import com.example.crud.infrastructure.adapters.output.persistence.entity.TacheEntity;
import com.example.crud.infrastructure.adapters.output.persistence.mapper.TacheMapper;
import com.example.crud.infrastructure.adapters.output.persistence.repository.TacheRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TacheService implements TacheUseCasePort {

    private final TacheRepository tacheRepository;
    private final TacheMapper tacheMapper; // Injecter TacheMapper

    public TacheService(TacheRepository tacheRepository, TacheMapper tacheMapper) {
        this.tacheRepository = tacheRepository;
        this.tacheMapper = tacheMapper;
    }

    @Override
    public Tache createTache(String nom, LocalDate date, EtatTache etat, Long idUser) {
        Tache tache = new Tache(nom, date, etat, idUser);
        TacheEntity tacheEntity = tacheMapper.toEntity(tache); // Utiliser l'instance de TacheMapper
        tacheEntity = tacheRepository.save(tacheEntity);
        return tacheMapper.toDomain(tacheEntity); // Utiliser l'instance de TacheMapper
    }

    @Override
    public Optional<Tache> getTache(Long id) {
        Optional<TacheEntity> tacheEntity = tacheRepository.findById(id);
        return tacheEntity.map(tacheMapper::toDomain); // Utiliser l'instance de TacheMapper
    }

    @Override
    public List<Tache> getAllTaches() {
        List<TacheEntity> tacheEntities = tacheRepository.findAll();
        return tacheEntities.stream().map(tacheMapper::toDomain).toList(); // Utiliser l'instance de TacheMapper
    }

    @Override
    public Tache updateTache(Long id, String nom, LocalDate date, EtatTache etat) {
        return tacheRepository.findById(id).map(tacheEntity -> {
            tacheEntity.setNom(nom);
            tacheEntity.setDate(date);
            tacheEntity.setEtat(etat);
            tacheEntity = tacheRepository.save(tacheEntity);
            return tacheMapper.toDomain(tacheEntity); // Utiliser l'instance de TacheMapper
        }).orElseThrow(() -> new RuntimeException("Tâche non trouvée avec l'ID : " + id));
    }

    @Override
    public void deleteTache(Long id) {
        tacheRepository.deleteById(id);
    }
}