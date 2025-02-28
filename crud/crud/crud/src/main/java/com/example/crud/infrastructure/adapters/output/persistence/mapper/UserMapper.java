package com.example.crud.infrastructure.adapters.output.persistence.mapper;


import com.example.crud.domain.model.User;
import com.example.crud.infrastructure.adapters.output.persistence.entity.UserEntity;

public class UserMapper {

    public static User toDomain(UserEntity userEntity) {
        return new User(userEntity.getNom(), userEntity.getPrenom(), userEntity.getEmail());
    }

    public static UserEntity toEntity(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setNom(user.getNom());
        userEntity.setPrenom(user.getPrenom());
        userEntity.setEmail(user.getEmail());
        return userEntity;
    }
}