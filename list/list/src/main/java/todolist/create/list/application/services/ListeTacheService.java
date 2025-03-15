package todolist.create.list.application.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import todolist.create.list.application.ports.input.ListeTacheUseCase;
import todolist.create.list.domain.model.EtatEnum;
import todolist.create.list.domain.model.ListeTache;
import todolist.create.list.infrasctructure.adapters.output.persistence.entity.ListeTacheEntity;
import todolist.create.list.infrasctructure.adapters.output.persistence.mapper.ListeTacheMapper;
import todolist.create.list.infrasctructure.adapters.output.persistence.repository.ListeTacheRepository;

@Service
public class ListeTacheService implements ListeTacheUseCase {
    
    private final ListeTacheRepository listeTacheRepository;
    private final ListeTacheMapper listeTacheMapper;

    public ListeTacheService(ListeTacheRepository listeTacheRepository, ListeTacheMapper listeTacheMapper) {
        this.listeTacheRepository = listeTacheRepository;
        this.listeTacheMapper = listeTacheMapper;
    }

    @Override
    public ListeTache saveListeTache(ListeTache listeTache) {
        ListeTacheEntity listeTacheEntity = listeTacheMapper.toEntity(listeTache);
        listeTacheEntity = listeTacheRepository.save(listeTacheEntity);
        return listeTacheMapper.toDomain(listeTacheEntity);
    }

    /*@Override
    public ListeTache createListeTache(String description, EtatEnum etat, Long idProjet, Long idTache, Long idUser) {
        ListeTache listeTache = new ListeTache(description, etat, idProjet, idTache, idUser);
        ListeTacheEntity listeTacheEntity = listeTacheMapper.toEntity(listeTache);
        listeTacheEntity = listeTacheRepository.save(listeTacheEntity);
        return listeTacheMapper.toDomain(listeTacheEntity);
    }*/

    @Override
    public ListeTache createListeTache(String description, EtatEnum etat, Long idProjet, Long idTache, Long idUser) {
        ListeTache listeTache = new ListeTache();
        listeTache.setDescription(description);
        listeTache.setEtat(etat);
        listeTache.setIdProjet(idProjet);
        listeTache.setIdTache(idTache);
        listeTache.setIdUser(idUser);

        ListeTacheEntity listeTacheEntity = listeTacheMapper.toEntity(listeTache);
        listeTacheEntity = listeTacheRepository.save(listeTacheEntity);
        return listeTacheMapper.toDomain(listeTacheEntity);
    }

    @Override
    public Optional<ListeTache> getListeTache(Long id) {
        Optional<ListeTacheEntity> listeTacheEntity = listeTacheRepository.findById(id);
        return listeTacheEntity.map(listeTacheMapper::toDomain);
    }

    @Override
    public List<ListeTache> getAllListeTaches() {
        List<ListeTacheEntity> listeDeTacheEntities = listeTacheRepository.findAll();
        return listeDeTacheEntities.stream()
            .map(listeTacheMapper::toDomain)
            .toList();
    }

    @Override
    public List<ListeTache> getListTachesByUserId(Long idUser) {
        List<ListeTacheEntity> listeDeTacheEntities = listeTacheRepository.findByUserId(idUser);
        return listeDeTacheEntities.stream()
                .map(listeTacheMapper::toDomain)
                .toList();
    }

    @Override
    public ListeTache updateListeTache(Long id, String description, EtatEnum etat) {
        return listeTacheRepository.findById(id).map(listeTacheEntity -> {
            listeTacheEntity.setDescription(description);
            listeTacheEntity.setEtat(etat);  // Ajout de l'état
            listeTacheEntity = listeTacheRepository.save(listeTacheEntity);
            return listeTacheMapper.toDomain(listeTacheEntity);
        }).orElseThrow(() -> new RuntimeException("Liste de tâches non trouvée avec l'ID : " + id));
    }
    

    @Override
    public void deleteListeTache(Long id) {
        listeTacheRepository.deleteById(id);
    }
}












