package com.example.crud.application.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.crud.domain.model.User;
import com.example.crud.infrastructure.adapters.output.persistence.entity.UserEntity;
import com.example.crud.infrastructure.adapters.output.persistence.mapper.UserMapper;
import com.example.crud.infrastructure.adapters.output.persistence.repository.UserRepository;
import com.example.crud.infrastructure.security.JwtUtil;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager, UserDetailsService userDetailsService,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Hacher le mot de passe
        UserEntity userEntity = UserMapper.toEntity(user); // Convertir User en UserEntity
        userEntity = userRepository.save(userEntity); // Enregistrer UserEntity
        return UserMapper.toDomain(userEntity); // Convertir UserEntity en User
    }

    public String authenticate(String username, String password) {
        // Authentifier l'utilisateur
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        // Charger les détails de l'utilisateur
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Générer et retourner le token JWT
        return jwtUtil.generateToken(userDetails);
    }
}