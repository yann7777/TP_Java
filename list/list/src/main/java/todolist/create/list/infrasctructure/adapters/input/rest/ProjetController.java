package todolist.create.list.infrasctructure.adapters.input.rest;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import todolist.create.list.application.ports.input.ProjetUseCase;
import todolist.create.list.application.services.CustomUserDetailsService;
import todolist.create.list.domain.model.Projet;

@RestController
@RequestMapping("/projets")
@CrossOrigin(origins = "**")
public class ProjetController {
    
    private final ProjetUseCase projetUseCase;
    private final CustomUserDetailsService userDetailsService; 

    public ProjetController(ProjetUseCase projetUseCase, CustomUserDetailsService userDetailsService) {
        this.projetUseCase = projetUseCase;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping
    public ResponseEntity<Projet> createProjet(@RequestBody Projet projet) {
        // Récupérer l'utilisateur connecté à partir du contexte de sécurité
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); 
        Long userId = userDetailsService.findUserIdByEmail(username);

        // Créer le projet avec l'ID de l'utilisateur connecté
        Projet createdProjet = projetUseCase.createProjet(
            projet.getNom(),
            userId
        );

        return ResponseEntity.ok(createdProjet);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Projet> getProjet(@PathVariable Long id) {
        Optional<Projet> projet = projetUseCase.getProjet(id);
        
        if (projet.isPresent()) {
            return new ResponseEntity<>(projet.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    
    @GetMapping
    public ResponseEntity<List<Projet>> getAllProjets() {
        List<Projet> projets = projetUseCase.getAllProjets();
        return new ResponseEntity<>(projets, HttpStatus.OK);
    }

    @GetMapping("/mes-projets")
    public ResponseEntity<List<Projet>> getProjetsByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Long userId = userDetailsService.findUserIdByEmail(username);

        List<Projet> projets = projetUseCase.getProjetsByUserId(userId);
        return new ResponseEntity<>(projets, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Projet> updateProjet(
            @PathVariable Long id,
            @RequestBody Map<String, String> requestBody) {
        try {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); 
            }

            // Récupérer l'email de l'utilisateur connecté
            String username = authentication.getName();

            Long userId = userDetailsService.findUserIdByEmail(username);

            // Récupérer le nouveau nom du projet depuis le corps de la requête
            String nouveauNom = requestBody.get("nom");

            // Mettre à jour le projet
            Projet updatedProjet = projetUseCase.updateProjet(id, nouveauNom, userId);

            if (updatedProjet != null) {
                return ResponseEntity.ok(updatedProjet);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la mise à jour du projet : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjet(@PathVariable Long id) {
        try {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Refuser l'accès si non authentifié
            }
    
            // Récupérer l'email de l'utilisateur connecté
            String username = authentication.getName(); // Récupère l'email de l'utilisateur
    
            Long userId = userDetailsService.findUserIdByEmail(username); // Utilisez CustomUserDetailsService
    
            // Supprimer le projet
            projetUseCase.deleteProjet(id, userId); // Appel du service adapté
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Accès refusé
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Projet non trouvé
        } catch (Exception e) {
            System.err.println("Erreur lors de la suppression du projet : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
