package todolist.create.list.infrasctructure.adapters;

import java.util.List;
import java.util.Optional;

import todolist.create.list.application.ports.output.ListeTacheRepositoryPort;
import todolist.create.list.domain.model.ListeTache;
import todolist.create.list.infrasctructure.adapters.output.persistence.entity.ListeTacheEntity;
import todolist.create.list.infrasctructure.adapters.output.persistence.mapper.ListeTacheMapper;
import todolist.create.list.infrasctructure.adapters.output.persistence.repository.ListeTacheRepository;

public class ListeTacheRepositoryAdapter implements ListeTacheRepositoryPort {
    private final ListeTacheRepository listeTacheRepository;
    private final ListeTacheMapper listeTacheMapper;

    public ListeTacheRepositoryAdapter(ListeTacheRepository listeTacheRepository, ListeTacheMapper listeTacheMapper) {
        this.listeTacheRepository = listeTacheRepository;
        this.listeTacheMapper = listeTacheMapper;
    }

    @Override
    public ListeTache save(ListeTache listeTache) {
        ListeTacheEntity listeTacheEntity = listeTacheMapper.toEntity(listeTache); // Conversion en entité
        listeTacheEntity = listeTacheRepository.save(listeTacheEntity); // Sauvegarde dans la base
        return listeTacheMapper.toDomain(listeTacheEntity); // Conversion en modèle de domaine
    }

    @Override
    public Optional<ListeTache> findById(Long id) {
        Optional<ListeTacheEntity> listeTacheEntity = listeTacheRepository.findById(id);
        return listeTacheEntity.map(listeTacheMapper::toDomain); // Conversion de l'entité en domaine
    }

    @Override
    public List<ListeTache> findAll() {
        List<ListeTacheEntity> listeTacheEntities = listeTacheRepository.findAll();
        return listeTacheEntities.stream()
            .map(listeTacheMapper::toDomain) // Conversion des entités en modèles de domaine
            .toList();
    }

    @Override
    public void deleteById(Long id) {
        listeTacheRepository.deleteById(id); // Suppression d'une tâche par son ID
    }
}
