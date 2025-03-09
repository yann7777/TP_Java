package com.example.crud.infrastructure.adapters.input.rest;

import com.example.crud.application.services.CustomUserDetailsService;
import com.example.crud.domain.model.User;
import com.example.crud.infrastructure.adapters.input.rest.dto.LoginRequest;
import com.example.crud.infrastructure.adapters.input.rest.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok("User logged in successfully!");
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        User user = new User(
                registerRequest.getNom(),
                registerRequest.getPrenom(),
                registerRequest.getEmail(),
                registerRequest.getPassword()
        );

        userDetailsService.registerUser(user);
        return ResponseEntity.ok("User registered successfully!");
    }
}