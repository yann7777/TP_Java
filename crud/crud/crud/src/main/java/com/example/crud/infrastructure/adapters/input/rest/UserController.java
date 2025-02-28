package com.example.crud.infrastructure.adapters.input.rest;

import com.example.crud.application.ports.input.UserCasePort;
import com.example.crud.domain.model.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Tag(name = "Utilisateurs", description = "API pour la gestion des utilisateurs")
@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*") 
public class UserController {

    private final UserCasePort userPort;

    public UserController(UserCasePort userPort) {
        this.userPort = userPort;
    }

    @Operation(summary = "Créer un utilisateur")
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userPort.createUser(user.getNom(), user.getPrenom(), user.getEmail()));
    }

    @Operation(summary = "Récupérer un utilisateur par ID")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        Optional<User> user = userPort.getUser(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Récupérer tous les utilisateurs")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userPort.getAllUsers());
    }

    @Operation(summary = "Mettre à jour un utilisateur (PUT)")
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        Optional<User> userOptional = userPort.getUser(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setNom(updatedUser.getNom());
            user.setPrenom(updatedUser.getPrenom());
            user.setEmail(updatedUser.getEmail());
            return ResponseEntity.ok(userPort.saveUser(user));
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Mettre à jour partiellement un utilisateur (PATCH)")
    @PatchMapping("/{id}")
    public ResponseEntity<User> partialUpdateUser(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Optional<User> userOptional = userPort.getUser(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            
            // Vérifier et mettre à jour chaque champ si présent dans la requête
            if (updates.containsKey("nom")) {
                user.setNom((String) updates.get("nom"));
            }
            if (updates.containsKey("prenom")) {
                user.setPrenom((String) updates.get("prenom"));
            }
            if (updates.containsKey("email")) {
                user.setEmail((String) updates.get("email"));
            }

            return ResponseEntity.ok(userPort.saveUser(user));
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Supprimer un utilisateur")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Optional<User> userOptional = userPort.getUser(id);
        if (userOptional.isPresent()) {
            userPort.deleteUser(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
