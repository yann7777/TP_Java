package todolist.create.list.infrasctructure.adapters.input.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import todolist.create.list.application.ports.input.ProjetUseCase;
import todolist.create.list.application.ports.input.TacheUseCase;
import todolist.create.list.application.services.CustomUserDetailsService;
import todolist.create.list.domain.model.Projet;
import todolist.create.list.domain.model.Tache;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/taches")
@CrossOrigin(origins = "**")
public class TacheController {

    private final TacheUseCase tachePort;
    private final CustomUserDetailsService userDetailsService; // Injectez CustomUserDetailsService
    private final ProjetUseCase projetUseCase; // Injectez ProjetUseCase


    public TacheController(TacheUseCase tachePort, CustomUserDetailsService userDetailsService, ProjetUseCase projetUseCase) {
        this.tachePort = tachePort;
        this.userDetailsService = userDetailsService;
        this.projetUseCase = projetUseCase;
    }

    @GetMapping("/mes-taches")
    public ResponseEntity<List<Tache>> getTachesByUser() {
        // Récupérer l'utilisateur connecté
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Récupère l'email de l'utilisateur

        // Récupérer l'ID de l'utilisateur à partir de son email
        Long userId = userDetailsService.findUserIdByEmail(username);

        // Récupérer les tâches de l'utilisateur
        List<Tache> taches = tachePort.getTachesByUserId(userId);
        return ResponseEntity.ok(taches);
    }

    /*@PostMapping
    public ResponseEntity<Tache> createTache(@RequestBody Tache tache) {
        System.out.println("ID utilisateur : " + tache.getIdUser());
        System.out.println("ID projet : " + tache.getIdProjet()); // Ajoutez ce log pour vérifier
    
        return ResponseEntity.ok(tachePort.createTache(
            tache.getTitre(),
            tache.getDescription(),
            tache.getEtat(),
            tache.getIdUser(),
            tache.getIdProjet() // Assurez-vous que ce champ est utilisé
        ));
    }*/

    /*@PostMapping
    public ResponseEntity<Tache> createTache(@RequestBody Tache tache) {
        // Récupérer l'utilisateur connecté à partir du contexte de sécurité
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Récupère l'email de l'utilisateur

        // Récupérer l'ID de l'utilisateur à partir de son email
        Long userId = userDetailsService.findUserIdByEmail(username);

        // Créer la tâche avec l'ID de l'utilisateur
        Tache createdTache = tachePort.createTache(
            tache.getTitre(),
            tache.getDescription(),
            tache.getEtat(),
            userId, // Utiliser l'ID de l'utilisateur connecté
            tache.getIdProjet() // Si nécessaire
        );

        return ResponseEntity.ok(createdTache);
    }*/


    @PostMapping
    public ResponseEntity<Tache> createTache(@RequestBody Tache tache) {
        try {
            // Récupérer l'utilisateur connecté
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Refuser l'accès si non authentifié
            }
    
            String username = authentication.getName(); // Récupère l'email de l'utilisateur
    
            // Récupérer l'ID de l'utilisateur à partir de son email
            Long userId = userDetailsService.findUserIdByEmail(username);
    
            // Créer la tâche avec l'ID de l'utilisateur
            Tache createdTache = tachePort.createTache(
                tache.getTitre(),
                tache.getDescription(),
                tache.getEtat(),
                userId, // Utiliser l'ID de l'utilisateur connecté
                tache.getIdProjet() // Si nécessaire
            );
    
            // Log la tâche créée
            System.out.println("Tâche créée : " + createdTache);
    
            return ResponseEntity.ok(createdTache);
        } catch (Exception e) {
            // Log l'erreur
            System.err.println("Erreur lors de la création de la tâche : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/projets")
    public ResponseEntity<List<Projet>> getProjetsByUser() {
        // Récupérer l'utilisateur connecté à partir du contexte de sécurité
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Récupère l'email de l'utilisateur

        // Récupérer l'ID de l'utilisateur à partir de son email
        Long userId = userDetailsService.findUserIdByEmail(username);

        // Récupérer les projets de l'utilisateur
        List<Projet> projets = projetUseCase.getProjetsByUserId(userId);

        return ResponseEntity.ok(projets);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Tache> getTache(@PathVariable Long id) {
        Optional<Tache> tache = tachePort.getTache(id);
        return tache.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Tache>> getAllTaches() {
        return ResponseEntity.ok(tachePort.getAllTaches());
    }

    @GetMapping("/user/{idUser}")
    public ResponseEntity<List<Tache>> getTachesByUserId(@PathVariable Long idUser) {
        List<Tache> taches = tachePort.getTachesByUserId(idUser);
        if (taches.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(taches);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tache> updateTache(@PathVariable Long id, @RequestBody Tache updatedTache) {
        return ResponseEntity.ok(tachePort.updateTache(id, updatedTache.getTitre(), updatedTache.getDescription(), updatedTache.getEtat()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTache(@PathVariable Long id) {
        tachePort.deleteTache(id);
        return ResponseEntity.noContent().build();
    }
}