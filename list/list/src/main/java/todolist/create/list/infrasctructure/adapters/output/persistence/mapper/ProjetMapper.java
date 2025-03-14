package todolist.create.list.infrasctructure.adapters.output.persistence.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import todolist.create.list.domain.model.Projet;
import todolist.create.list.infrasctructure.adapters.output.persistence.entity.ProjetEntity;
import todolist.create.list.infrasctructure.adapters.output.persistence.entity.UserEntity;
import todolist.create.list.infrasctructure.adapters.output.persistence.repository.UserRepository;

@Component
public class ProjetMapper {

 @Autowired
    private UserRepository userRepository;

    public Projet toDomain(ProjetEntity projetEntity) {
        return new Projet(
            projetEntity.getNom(),
            projetEntity.getUser().getId() // Récupérer l'ID de l'utilisateur
        );
    }

    public ProjetEntity toEntity(Projet projet) {
        ProjetEntity projetEntity = new ProjetEntity();
        projetEntity.setNom(projet.getNom());

        // Récupérer l'utilisateur à partir de l'ID
        UserEntity user = userRepository.findById(projet.getIdUser())
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + projet.getIdUser()));
        projetEntity.setUser(user); // Définir l'utilisateur

        return projetEntity;
    }

    public static Projet toModel(ProjetEntity projetEntity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toModel'");
    }
}
