package com.example.crud.application.services;

import com.example.crud.domain.model.User;
import com.example.crud.infrastructure.adapters.output.persistence.entity.UserEntity;
import com.example.crud.infrastructure.adapters.output.persistence.mapper.UserMapper;
import com.example.crud.infrastructure.adapters.output.persistence.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        User user = UserMapper.toDomain(userEntity);
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getAuthorities()
        );
    }

    // Ajouter la méthode registerUser
    public void registerUser(User user) {
        UserEntity userEntity = UserMapper.toEntity(user);
        userRepository.save(userEntity);
    }

    public User findUserByEmail(String email) {
        // Récupérer l'entité UserEntity à partir de l'email
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return UserMapper.toDomain(userEntity);
    }

    
}