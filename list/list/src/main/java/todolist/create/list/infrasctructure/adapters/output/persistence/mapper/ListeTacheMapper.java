package todolist.create.list.infrasctructure.adapters.output.persistence.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import todolist.create.list.domain.model.ListeTache;
import todolist.create.list.infrasctructure.adapters.output.persistence.entity.ListeTacheEntity;
import todolist.create.list.infrasctructure.adapters.output.persistence.entity.ProjetEntity;
import todolist.create.list.infrasctructure.adapters.output.persistence.entity.TacheEntity;
import todolist.create.list.infrasctructure.adapters.output.persistence.entity.UserEntity;
import todolist.create.list.infrasctructure.adapters.output.persistence.repository.ProjetRepository;
import todolist.create.list.infrasctructure.adapters.output.persistence.repository.TacheRepository;
import todolist.create.list.infrasctructure.adapters.output.persistence.repository.UserRepository;

@Component
public class ListeTacheMapper {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TacheRepository tacheRepository;

    @Autowired
    private ProjetRepository projetRepository;

    public ListeTache toDomain(ListeTacheEntity listeTacheEntity) {
        return new ListeTache(
            listeTacheEntity.getId(), // ID du projet
            listeTacheEntity.getDescription(),
            listeTacheEntity.getUser().getId(),  // Utilisateur ID
            listeTacheEntity.getProjet().getId(), // Projet ID (et non ProjetEntity)
            listeTacheEntity.getTache().getId(),   // Tâche ID
            listeTacheEntity.getDate(),
            listeTacheEntity.getEtat()
        );
    }


        public ListeTacheEntity toEntity(ListeTache listeTache) {
        ListeTacheEntity listeTacheEntity = new ListeTacheEntity();
        listeTacheEntity.setDescription(listeTache.getDescription());
        listeTacheEntity.setEtat(listeTache.getEtat());


        // Récupérer l'utilisateur à partir de l'ID
        UserEntity user = userRepository.findById(listeTache.getIdUser())
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + listeTache.getIdUser()));
        listeTacheEntity.setUser(user);

        // Récupérer la tâche à partir de l'ID
        TacheEntity tache = tacheRepository.findById(listeTache.getIdTache())
            .orElseThrow(() -> new RuntimeException("Tâche non trouvée avec l'ID : " + listeTache.getIdTache()));
        listeTacheEntity.setTache(tache);

            // Récupérer le projet à partir de l'ID
        ProjetEntity projet = projetRepository.findById(listeTache.getIdProjet())
            .orElseThrow(() -> new RuntimeException("Tâche non trouvée avec l'ID : " + listeTache.getIdProjet()));
        listeTacheEntity.setProjet(projet);

        return listeTacheEntity;
    }
}

