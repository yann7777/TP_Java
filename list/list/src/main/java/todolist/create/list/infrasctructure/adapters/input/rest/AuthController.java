package todolist.create.list.infrasctructure.adapters.input.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import todolist.create.list.application.services.CustomUserDetailsService;
import todolist.create.list.domain.model.User;
import todolist.create.list.infrasctructure.config.JwtUtils;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final CustomUserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    // Si @RequiredArgsConstructor ne fonctionne pas, définissez manuellement le constructeur :
    public AuthController(CustomUserDetailsService userDetailsService, AuthenticationManager authenticationManager, JwtUtils jwtUtils, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        try {
            if (user.getEmail() == null || user.getEmail().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("L'email est obligatoire");
            }
            if (user.getPassword() == null || user.getPassword().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le mot de passe est obligatoire");
            }
    
            log.info("Tentative d'enregistrement de l'utilisateur : {}", user.getEmail());
    
            // Encodage du mot de passe
            user.setPassword(passwordEncoder.encode(user.getPassword()));
    
            // Enregistrement de l'utilisateur
            userDetailsService.registerUser(user);
            log.info("Utilisateur enregistré avec succès : {}", user.getEmail());
    
            return ResponseEntity.ok("Utilisateur enregistré avec succès");
        } catch (Exception e) {
            log.error("Erreur lors de l'enregistrement de l'utilisateur : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'enregistrement");
        }
    }


    @PostMapping("/login")
public ResponseEntity<Object> login(@RequestBody User user) {
    try {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("L'email est obligatoire");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le mot de passe est obligatoire");
        }

        // Récupérer l'utilisateur depuis la base de données
        User existingUser = userDetailsService.findUserByEmail(user.getEmail());
        log.info("Utilisateur trouvé : {}", existingUser.getEmail());

        // Vérifier si le mot de passe correspond
        if (!passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            log.error("Mot de passe incorrect pour l'utilisateur : {}", user.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou mot de passe incorrect");
        }

        // Authentification avec Spring Security
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
        );
        log.info("Authentification réussie pour l'utilisateur : {}", user.getEmail());

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtils.generateToken(userDetails.getUsername());
        log.info("Token généré pour l'utilisateur : {}", user.getEmail());

        Map<String, Object> authData = new HashMap<>();
        authData.put("token", token);
        authData.put("type", "Bearer");

        return ResponseEntity.ok(authData);
    } catch (UsernameNotFoundException e) {
        log.error("Utilisateur non trouvé : {}", user.getEmail(), e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou mot de passe incorrect");
    } catch (AuthenticationException e) {
        log.error("Échec de l'authentification pour l'utilisateur : {}", user.getEmail(), e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Échec de l'authentification");
    }
}

    
    
}