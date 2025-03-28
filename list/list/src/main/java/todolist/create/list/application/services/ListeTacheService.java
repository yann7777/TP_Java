package todolist.create.list.application.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import todolist.create.list.application.ports.input.ListeTacheUseCase;
import todolist.create.list.domain.model.EtatEnum;
import todolist.create.list.domain.model.ListeTache;
import todolist.create.list.infrasctructure.adapters.output.persistence.entity.ListeTacheEntity;
import todolist.create.list.infrasctructure.adapters.output.persistence.entity.TacheEntity;
import todolist.create.list.infrasctructure.adapters.output.persistence.entity.UserEntity;
import todolist.create.list.infrasctructure.adapters.output.persistence.mapper.ListeTacheMapper;
import todolist.create.list.infrasctructure.adapters.output.persistence.repository.ListeTacheRepository;
import todolist.create.list.infrasctructure.adapters.output.persistence.repository.TacheRepository;
import todolist.create.list.infrasctructure.adapters.output.persistence.repository.UserRepository;

@Service
public class ListeTacheService implements ListeTacheUseCase {
    
    private final ListeTacheRepository listeTacheRepository;
    private final ListeTacheMapper listeTacheMapper;
    private final UserRepository userRepository;

    public ListeTacheService(ListeTacheRepository listeTacheRepository, ListeTacheMapper listeTacheMapper, UserRepository userRepository) {
        this.listeTacheRepository = listeTacheRepository;
        this.listeTacheMapper = listeTacheMapper;
        this.userRepository = userRepository;
    }

    @Override
    public ListeTache saveListeTache(ListeTache listeTache) {
        ListeTacheEntity listeTacheEntity = listeTacheMapper.toEntity(listeTache);
        listeTacheEntity = listeTacheRepository.save(listeTacheEntity);
        return listeTacheMapper.toDomain(listeTacheEntity);
    }


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
    public List<ListeTache> getListTachesByTacheId(Long tacheId) {
        List<ListeTacheEntity> listeTacheEntities = listeTacheRepository.findByTacheId(tacheId);
        return listeTacheEntities.stream()
                .map(listeTacheMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteListeTache(Long id) {
        listeTacheRepository.deleteById(id);
    }


    @Autowired
    private TacheRepository tacheRepository;

    @Override
    public ListeTache moveListeToTache(Long listeId, Long newTacheId, Long userId) {
        ListeTacheEntity liste = listeTacheRepository.findById(listeId)
            .orElseThrow(() -> new RuntimeException("Liste non trouvée"));
        
        // Vérifier que l'utilisateur est propriétaire de la liste
        if (!liste.getUser().getId().equals(userId)) {
            throw new RuntimeException("Non autorisé");
        }
        
        TacheEntity nouvelleTache = tacheRepository.findById(newTacheId)
            .orElseThrow(() -> new RuntimeException("Tâche non trouvée"));
        
        // Vérifier que l'utilisateur est propriétaire de la nouvelle tâche
        if (!nouvelleTache.getUser().getId().equals(userId)) {
            throw new RuntimeException("Non autorisé");
        }
        
        liste.setTache(nouvelleTache);
        liste = listeTacheRepository.save(liste);
        
        return listeTacheMapper.toDomain(liste);
    }


    @Override
    public ListeTache completedListeTache(Long id, Long userId){
        ListeTacheEntity liste = listeTacheRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Liste de tâche non trouvée"));

        if (!liste.getUser().getId().equals(userId)) {
            throw new RuntimeException("Non autorisé");
        }

        liste.setCompleted(true);
        liste = listeTacheRepository.save(liste);

        return listeTacheMapper.toDomain(liste);
    }

    @Override
    public ListeTache uncompletedListeTache(Long id, Long userId){
        ListeTacheEntity liste = listeTacheRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Liste de sous tâches non autorisées"));

        if (!liste.getUser().getId().equals(userId)) {
            throw new RuntimeException("Non autorisé");
        }

        liste.setCompleted(false);
        liste = listeTacheRepository.save(liste);

        return listeTacheMapper.toDomain(liste);
    }

    @Override
    public List<ListeTache> getCompletedListeTache(Long userId){
        UserEntity user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("Utilisateur non autorisé"));
    
        List<ListeTacheEntity> listes = listeTacheRepository.findByUserAndCompleted(user, true);
        
        return listes.stream().map(listeTacheMapper::toDomain).toList();
    }
}












