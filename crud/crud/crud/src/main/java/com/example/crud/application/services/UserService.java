package com.example.crud.application.services;

import org.springframework.stereotype.Service;

import com.example.crud.application.ports.input.UserCasePort;
import com.example.crud.domain.model.User;
import com.example.crud.infrastructure.adapters.output.persistence.entity.UserEntity;
import com.example.crud.infrastructure.adapters.output.persistence.mapper.UserMapper;
import com.example.crud.infrastructure.adapters.output.persistence.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserCasePort {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(String nom, String prenom, String email) {
        User user = new User(nom, prenom, email);
        UserEntity userEntity = UserMapper.toEntity(user);
        userEntity = userRepository.save(userEntity); 
        return UserMapper.toDomain(userEntity); 
    }

    @Override
    public Optional<User> getUser(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id); 
        return userEntity.map(UserMapper::toDomain); 
    }

    @Override
    public List<User> getAllUsers() {
        List<UserEntity> userEntities = userRepository.findAll(); 
        return userEntities.stream().map(UserMapper::toDomain).toList(); 
    }

    @Override
    public User saveUser(User user) {
        UserEntity userEntity = UserMapper.toEntity(user);
        userEntity = userRepository.save(userEntity); 
        return UserMapper.toDomain(userEntity);     }

    @Override
    public User updateUser(Long id, String nom, String prenom, String email) {
        return userRepository.findById(id).map(userEntity -> {
            userEntity.setNom(nom);
            userEntity.setPrenom(prenom);
            userEntity.setEmail(email);
            userEntity = userRepository.save(userEntity); 
            return UserMapper.toDomain(userEntity); 
        }).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + id));
    }
    
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
