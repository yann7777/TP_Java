package com.example.crud.infrastructure.adapters.output.persistence.mapper;

import com.example.crud.domain.model.Tache;
import com.example.crud.infrastructure.adapters.output.persistence.entity.TacheEntity;
import com.example.crud.infrastructure.adapters.output.persistence.entity.UserEntity;
import com.example.crud.infrastructure.adapters.output.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component // Marquez cette classe comme un composant Spring
public class TacheMapper {

    @Autowired
    private UserRepository userRepository;

    public Tache toDomain(TacheEntity tacheEntity) {
        return new Tache(
            tacheEntity.getNom(),
            tacheEntity.getDate(),
            tacheEntity.getEtat(),
            tacheEntity.getUser().getId() // Récupérer l'ID de l'utilisateur
        );
    }

    public TacheEntity toEntity(Tache tache) {
        TacheEntity tacheEntity = new TacheEntity();
        tacheEntity.setNom(tache.getNom());
        tacheEntity.setDate(tache.getDate());
        tacheEntity.setEtat(tache.getEtat());

        // Récupérer l'utilisateur à partir de l'ID
        UserEntity user = userRepository.findById(tache.getIdUser())
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + tache.getIdUser()));
        tacheEntity.setUser(user); // Définir l'utilisateur

        return tacheEntity;
    }
}