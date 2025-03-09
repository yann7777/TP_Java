package com.example.crud.infrastructure.adapters.output.persistence;

import com.example.crud.application.ports.output.UserRepositoryPort;
import com.example.crud.domain.model.User;
import com.example.crud.infrastructure.adapters.output.persistence.entity.UserEntity;
import com.example.crud.infrastructure.adapters.output.persistence.mapper.UserMapper;
import com.example.crud.infrastructure.adapters.output.persistence.repository.UserRepository;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = UserMapper.toEntity(user);
        userEntity = userRepository.save(userEntity);
        return UserMapper.toDomain(userEntity);
    }

    @Override
    public Optional<User> findById(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        return userEntity.map(UserMapper::toDomain);
    }

    @Override
    public List<User> findAll() {
        List<UserEntity> userEntities = userRepository.findAll();
        return userEntities.stream().map(UserMapper::toDomain).toList();
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
