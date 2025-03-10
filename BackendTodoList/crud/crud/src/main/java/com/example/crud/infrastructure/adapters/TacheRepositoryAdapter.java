package com.example.crud.infrastructure.adapters;

import com.example.crud.application.ports.output.TacheRepositoryPort;
import com.example.crud.domain.model.Tache;
import com.example.crud.infrastructure.adapters.output.persistence.entity.TacheEntity;
import com.example.crud.infrastructure.adapters.output.persistence.mapper.TacheMapper;
import com.example.crud.infrastructure.adapters.output.persistence.repository.TacheRepository;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

@Repository
public class TacheRepositoryAdapter implements TacheRepositoryPort {

    private final TacheRepository tacheRepository;
    private final TacheMapper tacheMapper; // Inject TacheMapper

    @Autowired
    public TacheRepositoryAdapter(TacheRepository tacheRepository, TacheMapper tacheMapper) {
        this.tacheRepository = tacheRepository;
        this.tacheMapper = tacheMapper; // Initialize the mapper
    }

    @Override
    public Tache save(Tache tache) {
        TacheEntity tacheEntity = tacheMapper.toEntity(tache); // Use instance method
        tacheEntity = tacheRepository.save(tacheEntity);
        return tacheMapper.toDomain(tacheEntity); // Use instance method
    }

    @Override
    public Optional<Tache> findById(Long id) {
        Optional<TacheEntity> tacheEntity = tacheRepository.findById(id);
        return tacheEntity.map(tacheMapper::toDomain); // Use instance method
    }

    @Override
    public List<Tache> findAll() {
        List<TacheEntity> tacheEntities = tacheRepository.findAll();
        return tacheEntities.stream()
            .map(tacheMapper::toDomain) // Use instance method
            .toList();
    }

    @Override
    public void deleteById(Long id) {
        tacheRepository.deleteById(id);
    }
}