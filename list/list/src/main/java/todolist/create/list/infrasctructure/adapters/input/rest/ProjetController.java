package todolist.create.list.infrasctructure.adapters.input.rest;

import java.util.List;
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
    private final CustomUserDetailsService userDetailsService; // Injectez CustomUserDetailsService


    public ProjetController(ProjetUseCase projetUseCase, CustomUserDetailsService userDetailsService) {
        this.projetUseCase = projetUseCase;
        this.userDetailsService = userDetailsService;
    }


    /*@PostMapping
    public ResponseEntity<Projet> createProjet(@RequestBody Projet projet) {
        return ResponseEntity.ok(projetUseCase.createProjet(
            projet.getNom(),
            projet.getIdUser()
        ));
    }*/

    @PostMapping
    public ResponseEntity<Projet> createProjet(@RequestBody Projet projet) {
        // Récupérer l'utilisateur connecté à partir du contexte de sécurité
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Récupère l'email de l'utilisateur

        // Récupérer l'ID de l'utilisateur à partir de son email
        Long userId = userDetailsService.findUserIdByEmail(username);

        // Créer le projet avec l'ID de l'utilisateur connecté
        Projet createdProjet = projetUseCase.createProjet(
            projet.getNom(),
            userId // Utiliser l'ID de l'utilisateur connecté
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
        // Récupérer l'utilisateur connecté
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Récupère l'email de l'utilisateur

        // Récupérer l'ID de l'utilisateur à partir de son email
        Long userId = userDetailsService.findUserIdByEmail(username);

        // Récupérer les projets de l'utilisateur
        List<Projet> projets = projetUseCase.getProjetsByUserId(userId);
        return new ResponseEntity<>(projets, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Projet> updateProjet(@PathVariable Long id, @RequestBody String nom) {
        Projet updatedProjet = projetUseCase.updateProjet(id, nom);
        
        if (updatedProjet != null) {
            return new ResponseEntity<>(updatedProjet, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjet(@PathVariable Long id) {
        Optional<Projet> projetOptional = projetUseCase.getProjet(id);
        if (projetOptional.isPresent()) {
            projetUseCase.deleteProjet(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
