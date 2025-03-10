package com.example.crud.infrastructure.adapters.input.rest;

import com.example.crud.application.services.CustomUserDetailsService;
import com.example.crud.domain.model.User;
import com.example.crud.infrastructure.adapters.input.rest.dto.LoginRequest;
import com.example.crud.infrastructure.adapters.input.rest.dto.RegisterRequest;
import com.example.crud.infrastructure.config.JwtTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            // Authentification de l'utilisateur
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
    
            // Définition de l'authentification dans le contexte de sécurité
            SecurityContextHolder.getContext().setAuthentication(authentication);
    
            // Récupérer l'utilisateur authentifié
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = userDetailsService.findUserByEmail(userDetails.getUsername());
            String token = jwtTokenProvider.generateToken(userDetails);
    
            // Renvoyer une réponse JSON de succès avec les informations de l'utilisateur
            Map<String, Object> response = new HashMap<>();
            response.put("message", "User logged in successfully!");
            response.put("token", token); // Correction : ajouter le token JWT généré
            response.put("user", user); // Ajouter les informations de l'utilisateur
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Renvoyer une réponse JSON en cas d'erreur
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid email or password");
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        try {
            // Hachage du mot de passe
            String hashedPassword = bCryptPasswordEncoder.encode(registerRequest.getPassword());

            // Création d'un nouvel utilisateur
            User user = new User(
                    registerRequest.getNom(),
                    registerRequest.getPrenom(),
                    registerRequest.getEmail(),
                    hashedPassword
            );

            // Enregistrement de l'utilisateur
            userDetailsService.registerUser(user);

            // Renvoyer une réponse JSON de succès
            Map<String, String> response = new HashMap<>();
            response.put("message", "User registered successfully!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Renvoyer une réponse JSON en cas d'erreur
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}