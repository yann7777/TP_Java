package com.example.crud.infrastructure.adapters.output.persistence.mapper;

import com.example.crud.domain.model.ListeDeTache;
import com.example.crud.infrastructure.adapters.output.persistence.entity.ListeDeTacheEntity;
import com.example.crud.infrastructure.adapters.output.persistence.entity.UserEntity;
import com.example.crud.infrastructure.adapters.output.persistence.entity.TacheEntity;
import com.example.crud.infrastructure.adapters.output.persistence.repository.UserRepository;
import com.example.crud.infrastructure.adapters.output.persistence.repository.TacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ListeDeTacheMapper {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TacheRepository tacheRepository;

    public ListeDeTache toDomain(ListeDeTacheEntity listeDeTacheEntity) {
        return new ListeDeTache(
            listeDeTacheEntity.getNom(),
            listeDeTacheEntity.getDate(),
            listeDeTacheEntity.getEtat(),
            listeDeTacheEntity.getUser().getId(), // Récupérer l'ID de l'utilisateur
            listeDeTacheEntity.getTache().getId()  // Récupérer l'ID de la tâche
        );
    }

    public ListeDeTacheEntity toEntity(ListeDeTache listeDeTache) {
        ListeDeTacheEntity listeDeTacheEntity = new ListeDeTacheEntity();
        listeDeTacheEntity.setNom(listeDeTache.getNom());
        listeDeTacheEntity.setDate(listeDeTache.getDate());
        listeDeTacheEntity.setEtat(listeDeTache.getEtat());

        // Récupérer l'utilisateur à partir de l'ID
        UserEntity user = userRepository.findById(listeDeTache.getIdUser())
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + listeDeTache.getIdUser()));
        listeDeTacheEntity.setUser(user);

        // Récupérer la tâche à partir de l'ID
        TacheEntity tache = tacheRepository.findById(listeDeTache.getIdTache())
            .orElseThrow(() -> new RuntimeException("Tâche non trouvée avec l'ID : " + listeDeTache.getIdTache()));
        listeDeTacheEntity.setTache(tache);

        return listeDeTacheEntity;
    }
}