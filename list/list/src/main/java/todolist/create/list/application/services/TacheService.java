package todolist.create.list.application.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import todolist.create.list.domain.model.EtatEnum;
import todolist.create.list.domain.model.Tache;
import todolist.create.list.infrasctructure.adapters.output.persistence.entity.ProjetEntity;
import todolist.create.list.infrasctructure.adapters.output.persistence.entity.TacheEntity;
import todolist.create.list.infrasctructure.adapters.output.persistence.entity.UserEntity;
import todolist.create.list.infrasctructure.adapters.output.persistence.mapper.TacheMapper;
import todolist.create.list.infrasctructure.adapters.output.persistence.repository.ProjetRepository;
import todolist.create.list.infrasctructure.adapters.output.persistence.repository.TacheRepository;
import todolist.create.list.infrasctructure.adapters.output.persistence.repository.UserRepository;
import todolist.create.list.application.ports.input.TacheUseCase;

@Service
public class TacheService implements TacheUseCase {

    private final TacheRepository tacheRepository;
    private final TacheMapper tacheMapper;
    private final UserRepository userRepository;
    private final ProjetRepository projetRepository; 

    public TacheService(TacheRepository tacheRepository, TacheMapper tacheMapper, UserRepository userRepository, ProjetRepository projetRepository) {
        this.tacheRepository = tacheRepository;
        this.tacheMapper = tacheMapper;
        this.userRepository = userRepository;
        this.projetRepository = projetRepository; 
    }


    @Override
    public Tache createTache(String titre, String description, EtatEnum etat, Long idUser, Long idProjet, LocalDateTime dateRappel, boolean pinned, boolean archived) {
        System.out.println("ID utilisateur : " + idUser);
        System.out.println("ID projet : " + idProjet);
    
        // Vérifier que l'utilisateur existe
        UserEntity user = userRepository.findById(idUser)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + idUser));
    
        // Vérifier que le projet existe
        ProjetEntity projet = projetRepository.findById(idProjet)
            .orElseThrow(() -> new RuntimeException("Projet non trouvé avec l'ID : " + idProjet));
    
        // Créer la tâche
        Tache tache = new Tache(titre, description, etat, idUser, idProjet, dateRappel, pinned, archived);
        TacheEntity tacheEntity = tacheMapper.toEntity(tache);
        tacheEntity.setUser(user);
        tacheEntity.setProjet(projet);
    
        // Enregistrer la tâche
        tacheEntity = tacheRepository.save(tacheEntity);
        return tacheMapper.toDomain(tacheEntity);
    }

    @Override
    public Optional<Tache> getTache(Long id) {
        Optional<TacheEntity> tacheEntity = tacheRepository.findById(id);
        return tacheEntity.map(tacheMapper::toDomain);
    }

    @Override
    public List<Tache> getAllTaches() {
        List<TacheEntity> tacheEntities = tacheRepository.findAll();
        return tacheEntities.stream().map(tacheMapper::toDomain).toList();
    }

    @Override
    public Tache saveTache(Tache tache) {
        TacheEntity tacheEntity = tacheMapper.toEntity(tache);
        tacheEntity = tacheRepository.save(tacheEntity);
        return tacheMapper.toDomain(tacheEntity);
    }

    @Override
    public Tache updateTache(Long id, String titre, String description, EtatEnum etat, LocalDateTime dateRappel, Long userId) {
        TacheEntity tacheEntity = tacheRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tâche non trouvée avec l'ID : " + id));
        
        if (!tacheEntity.getUser().getId().equals(userId) && (tacheEntity.getAssignee() == null || !tacheEntity.getAssignee().getId().equals(userId))) {
            throw new RuntimeException("Vous n'êtes pas autorisé à modifier cette tâche.");
        }
        
        tacheEntity.setTitre(titre);
        tacheEntity.setDescription(description);
        tacheEntity.setEtat(etat);
        tacheEntity.setDateRappel(dateRappel);
        tacheEntity = tacheRepository.save(tacheEntity);
        return tacheMapper.toDomain(tacheEntity);
    }

    @Override
    public Tache pinTache(Long id, Long userId) {
        TacheEntity tacheEntity = tacheRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tâche non trouvée avec l'ID : " + id));

        // Vérifier que l'utilisateur est le créateur de la tâche
        if (!tacheEntity.getUser().getId().equals(userId)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à épingler cette tâche.");
        }

        tacheEntity.setPinned(true); // Épingler la tâche
        tacheEntity = tacheRepository.save(tacheEntity);
        return tacheMapper.toDomain(tacheEntity);
    }

    @Override
    public Tache unpinTache(Long id, Long userId) {
        TacheEntity tacheEntity = tacheRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tâche non trouvée avec l'ID : " + id));

        // Vérifier que l'utilisateur est le créateur de la tâche
        if (!tacheEntity.getUser().getId().equals(userId)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à désépingler cette tâche.");
        }

        tacheEntity.setPinned(false); // Désépingler la tâche
        tacheEntity = tacheRepository.save(tacheEntity);
        return tacheMapper.toDomain(tacheEntity);
    }

    @Override
    public void deleteTache(Long id, Long userId) {
        TacheEntity tacheEntity = tacheRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tâche non trouvée avec l'ID : " + id));
        
        if (!tacheEntity.getUser().getId().equals(userId)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à supprimer cette tâche.");
        }
        
        tacheRepository.deleteById(id);
    }


    @Override
    public List<Tache> rechercherTaches(Long idUser, String terme) {
        UserEntity user = userRepository.findById(idUser)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé : " + idUser));
        List<TacheEntity> tacheEntities;
        if (terme == null || terme.trim().isEmpty()) {
            tacheEntities = tacheRepository.findByUser(user);
        } else {
            tacheEntities = tacheRepository.findByUserAndTitreContainingIgnoreCaseOrUserAndDescriptionContainingIgnoreCase(
                user, terme, user, terme);
        }
        return tacheEntities.stream().map(tacheMapper::toDomain).toList();
    }


    @Override
    public List<Tache> getTachesByUserId(Long idUser) {
        UserEntity user = userRepository.findById(idUser)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + idUser));
        List<TacheEntity> tacheEntities = tacheRepository.findByUser(user);
        return tacheEntities.stream().map(tacheMapper::toDomain).toList();
    }


    @Override
    public Tache assignTache(Long tacheId, Long assigneeId, Long currentUserId){
        TacheEntity tacheEntity = tacheRepository.findById(tacheId)
        .orElseThrow(() -> new RuntimeException( "Tâche non trouvée avec l'id " + tacheId));

        if (!tacheEntity.getUser().getId().equals(currentUserId)) {
            throw new RuntimeException("Seul le créateur de la tâche peut l'assigner");
        }

        UserEntity assignee = userRepository.findById(assigneeId)
        .orElseThrow(() -> new RuntimeException("Utilisateur assigné non trouvé avec l'id : " + assigneeId));

        tacheEntity.setAssignee(assignee);
        tacheEntity = tacheRepository.save(tacheEntity);
        
        return tacheMapper.toDomain(tacheEntity);
    }

    @Override
    public Tache unassignTache(Long tacheId, Long currentUserId){
        TacheEntity tacheEntity = tacheRepository.findById(tacheId)
        .orElseThrow(() -> new RuntimeException("Tâche non trouvée avec l'id : " + tacheId));

        if (!tacheEntity.getUser().getId().equals(currentUserId) && (tacheEntity.getAssignee() == null || !tacheEntity.getAssignee().getId().equals(currentUserId))) {
            throw new RuntimeException("Vous n'êtes pas autorisé à désassigner cette tâche.");
        }

        tacheEntity.setAssignee(null);
        tacheEntity = tacheRepository.save(tacheEntity);
        return tacheMapper.toDomain(tacheEntity);
    }

    @Override
    public List<Tache> getAssignedTaches(Long userId){
        UserEntity user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'id :" + userId));

        List<TacheEntity> tacheEntities = tacheRepository.findByAssignee(user);
        return tacheEntities.stream().map(tacheMapper::toDomain).toList();
    }


    @Override
    public Tache archivedTache(Long id, Long userId){
        TacheEntity tacheEntity = tacheRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Tâche non trouvée avec l'id :" +id));

        if (!tacheEntity.getUser().getId().equals(userId)) {
            throw new RuntimeException("Vous êtes pas autorisé à archiver cette tâche");
        }

        tacheEntity.setArchived(true);
        tacheEntity = tacheRepository.save(tacheEntity);
        return tacheMapper.toDomain(tacheEntity);

    }

    @Override
    public Tache unarchivedTache(Long id, Long userId){
        TacheEntity tacheEntity = tacheRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Tâche non trouvée avec l'id :" +id));

        if (!tacheEntity.getUser().getId().equals(userId)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à désarchiver cette tâche");
        }

        tacheEntity.setArchived(false);
        tacheEntity = tacheRepository.save(tacheEntity);
        return tacheMapper.toDomain(tacheEntity);
    }


    @Override
    public List<Tache> getArchivedTaches(Long userId){
        UserEntity userEntity = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'id :" +userId));

        List<TacheEntity> tacheEntities = tacheRepository.findByUserAndArchived(userEntity, true);
        return tacheEntities.stream().map(tacheMapper::toDomain).toList();
    }
}