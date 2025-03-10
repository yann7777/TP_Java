package com.example.crud.infrastructure.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
            http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
            .userDetailsService(userDetailsService)
            .passwordEncoder(bCryptPasswordEncoder);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Désactiver CSRF
            .authorizeHttpRequests(auth -> auth
            .requestMatchers( "/auth/register", "/auth/login").permitAll()
            .anyRequest().authenticated() // Toutes les autres requêtes nécessitent une authentification
            )
            .formLogin(form -> form
                .loginPage("/login") 
                .defaultSuccessUrl("/users", true) // Redirection après connexion réussie
                .permitAll() // Autoriser l'accès à la page de connexion
            )
            .logout(logout -> logout
                .logoutUrl("/logout") // URL pour la déconnexion
                .logoutSuccessUrl("/login?logout") // Redirection après déconnexion
                .invalidateHttpSession(true) // Invalider la session
                .deleteCookies("JSESSIONID") // Supprimer les cookies
                .permitAll() // Autoriser l'accès à la déconnexion
            );

        return http.build();
    }

}