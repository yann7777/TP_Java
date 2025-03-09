package com.example.crud.infrastructure.adapters.input.rest;

import com.example.crud.application.ports.input.TacheUseCasePort;
import com.example.crud.domain.model.Tache;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Tâches", description = "API pour la gestion des tâches")
@RestController
@RequestMapping("/taches")
@CrossOrigin(origins = "*")
public class TacheController {

    private final TacheUseCasePort tachePort;

    public TacheController(TacheUseCasePort tachePort) {
        this.tachePort = tachePort;
    }

    @Operation(summary = "Créer une tâche")
    @PostMapping
    public ResponseEntity<Tache> createTache(@RequestBody Tache tache) {
        return ResponseEntity.ok(tachePort.createTache(
            tache.getNom(),
            tache.getDate(),
            tache.getEtat(),
            tache.getIdUser() // Utiliser getIdUser() au lieu de getUser().getId()
        ));
    }

    @Operation(summary = "Récupérer une tâche par ID")
    @GetMapping("/{id}")
    public ResponseEntity<Tache> getTache(@PathVariable Long id) {
        Optional<Tache> tache = tachePort.getTache(id);
        return tache.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Récupérer toutes les tâches")
    @GetMapping
    public ResponseEntity<List<Tache>> getAllTaches() {
        return ResponseEntity.ok(tachePort.getAllTaches());
    }

    @Operation(summary = "Mettre à jour une tâche (PUT)")
    @PutMapping("/{id}")
    public ResponseEntity<Tache> updateTache(@PathVariable Long id, @RequestBody Tache updatedTache) {
        return ResponseEntity.ok(tachePort.updateTache(id, updatedTache.getNom(), updatedTache.getDate(), updatedTache.getEtat()));
    }

    @Operation(summary = "Supprimer une tâche")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTache(@PathVariable Long id) {
        tachePort.deleteTache(id);
        return ResponseEntity.noContent().build();
    }
}