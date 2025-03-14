package todolist.create.list.infrasctructure.adapters;

import java.util.List;
import java.util.Optional;

import todolist.create.list.application.ports.output.TacheRepositoryPort;
import todolist.create.list.domain.model.Tache;
import todolist.create.list.infrasctructure.adapters.output.persistence.entity.TacheEntity;
import todolist.create.list.infrasctructure.adapters.output.persistence.mapper.TacheMapper;
import todolist.create.list.infrasctructure.adapters.output.persistence.repository.TacheRepository;

public class TacheRepositoryAdapter implements TacheRepositoryPort {
    private final TacheRepository tacheRepository;
    private final TacheMapper tacheMapper; // Inject TacheMapper

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
