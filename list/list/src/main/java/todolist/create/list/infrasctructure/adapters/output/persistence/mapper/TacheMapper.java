package todolist.create.list.infrasctructure.adapters.output.persistence.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import todolist.create.list.domain.model.Tache;
import todolist.create.list.infrasctructure.adapters.output.persistence.entity.TacheEntity;
import todolist.create.list.infrasctructure.adapters.output.persistence.entity.UserEntity;
import todolist.create.list.infrasctructure.adapters.output.persistence.entity.ProjetEntity;
import todolist.create.list.infrasctructure.adapters.output.persistence.repository.UserRepository;
import todolist.create.list.infrasctructure.adapters.output.persistence.repository.ProjetRepository;

@Component
public class TacheMapper {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjetRepository projetRepository;

    public Tache toDomain(TacheEntity tacheEntity) {
        return new Tache(
            tacheEntity.getId(),
            tacheEntity.getTitre(),
            tacheEntity.getDescription(),
            tacheEntity.getEtat(),
            tacheEntity.getUser().getId(),  // Récupérer l'ID de l'utilisateur
            tacheEntity.getProjet().getId(), // Récupérer l'ID du projet
            tacheEntity.getDateRappel()
        );
    }

    public TacheEntity toEntity(Tache tache) {
        TacheEntity tacheEntity = new TacheEntity();
        tacheEntity.setTitre(tache.getTitre());
        tacheEntity.setDescription(tache.getDescription());
        tacheEntity.setEtat(tache.getEtat());
        tacheEntity.setDateRappel(tache.getDateRappel());
    
        // Récupérer l'utilisateur par son ID
        UserEntity user = userRepository.findById(tache.getIdUser())
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + tache.getIdUser()));
        tacheEntity.setUser(user);
    
        // Récupérer le projet par son ID
        ProjetEntity projet = projetRepository.findById(tache.getIdProjet())
            .orElseThrow(() -> new RuntimeException("Projet non trouvé avec l'ID : " + tache.getIdProjet()));
        tacheEntity.setProjet(projet);
    
        return tacheEntity;
    }
}