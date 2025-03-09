package com.example.crud.infrastructure.adapters.output.persistence.mapper;

import com.example.crud.domain.model.User;
import com.example.crud.infrastructure.adapters.output.persistence.entity.UserEntity;

public class UserMapper {

    // Convertir l'entité UserEntity en objet métier User
    public static User toDomain(UserEntity userEntity) {
        return new User(userEntity.getNom(), userEntity.getPrenom(), userEntity.getEmail(), userEntity.getPassword());
    }

    // Convertir l'objet métier User en entité UserEntity
    public static UserEntity toEntity(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setNom(user.getNom());
        userEntity.setPrenom(user.getPrenom());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(user.getPassword());  // Ajouter le mot de passe dans l'entité
        return userEntity;
    }

    // Cette méthode n'est pas utilisée ici, donc vous pouvez la supprimer ou la compléter si nécessaire
    public static User toDomain(User user) {
        throw new UnsupportedOperationException("Unimplemented method 'toDomain'");
    }
}
