package com.example.crud.infrastructure.adapters;

import com.example.crud.application.ports.output.ListeDeTacheRepositoryPort;
import com.example.crud.domain.model.ListeDeTache;
import com.example.crud.infrastructure.adapters.output.persistence.entity.ListeDeTacheEntity;
import com.example.crud.infrastructure.adapters.output.persistence.mapper.ListeDeTacheMapper;
import com.example.crud.infrastructure.adapters.output.persistence.repository.ListeDeTacheRepository;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

@Repository
public class ListeDeTacheRepositoryAdapter implements ListeDeTacheRepositoryPort {

    private final ListeDeTacheRepository listeDeTacheRepository;
    private final ListeDeTacheMapper listeDeTacheMapper; // Inject ListeDeTacheMapper

    @Autowired
    public ListeDeTacheRepositoryAdapter(ListeDeTacheRepository listeDeTacheRepository, ListeDeTacheMapper listeDeTacheMapper) {
        this.listeDeTacheRepository = listeDeTacheRepository;
        this.listeDeTacheMapper = listeDeTacheMapper; // Initialize the mapper
    }

    @Override
    public ListeDeTache save(ListeDeTache listeDeTache) {
        ListeDeTacheEntity listeDeTacheEntity = listeDeTacheMapper.toEntity(listeDeTache); // Use instance method
        listeDeTacheEntity = listeDeTacheRepository.save(listeDeTacheEntity);
        return listeDeTacheMapper.toDomain(listeDeTacheEntity); // Use instance method
    }

    @Override
    public Optional<ListeDeTache> findById(Long id) {
        Optional<ListeDeTacheEntity> listeDeTacheEntity = listeDeTacheRepository.findById(id);
        return listeDeTacheEntity.map(listeDeTacheMapper::toDomain); // Use instance method
    }

    @Override
    public List<ListeDeTache> findAll() {
        List<ListeDeTacheEntity> listeDeTacheEntities = listeDeTacheRepository.findAll();
        return listeDeTacheEntities.stream()
            .map(listeDeTacheMapper::toDomain) // Use instance method
            .toList();
    }

    @Override
    public void deleteById(Long id) {
        listeDeTacheRepository.deleteById(id);
    }
}