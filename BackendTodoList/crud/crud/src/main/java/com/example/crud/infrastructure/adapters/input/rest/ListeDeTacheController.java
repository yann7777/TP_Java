package com.example.crud.infrastructure.adapters.input.rest;

import com.example.crud.application.ports.input.ListeDeTacheUseCasePort;
import com.example.crud.domain.model.ListeDeTache;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Listes de tâches", description = "API pour la gestion des listes de tâches")
@RestController
@RequestMapping("/listesdetaches")
@CrossOrigin(origins = "*")
public class ListeDeTacheController {

    private final ListeDeTacheUseCasePort listeDeTachePort;

    public ListeDeTacheController(ListeDeTacheUseCasePort listeDeTachePort) {
        this.listeDeTachePort = listeDeTachePort;
    }

    @Operation(summary = "Créer une liste de tâches")
    @PostMapping
    public ResponseEntity<ListeDeTache> createListeDeTache(@RequestBody ListeDeTache listeDeTache) {
        return ResponseEntity.ok(listeDeTachePort.createListeDeTache(
            listeDeTache.getNom(),
            listeDeTache.getDate(),
            listeDeTache.getEtat(),
            listeDeTache.getIdUser(), // Utiliser idUser
            listeDeTache.getIdTache() // Utiliser idTache
        ));
    }

    @Operation(summary = "Récupérer une liste de tâches par ID")
    @GetMapping("/{id}")
    public ResponseEntity<ListeDeTache> getListeDeTache(@PathVariable Long id) {
        Optional<ListeDeTache> listeDeTache = listeDeTachePort.getListeDeTache(id);
        return listeDeTache.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Récupérer toutes les listes de tâches")
    @GetMapping
    public ResponseEntity<List<ListeDeTache>> getAllListeDeTaches() {
        return ResponseEntity.ok(listeDeTachePort.getAllListeDeTaches());
    }

    @Operation(summary = "Mettre à jour une liste de tâches (PUT)")
    @PutMapping("/{id}")
    public ResponseEntity<ListeDeTache> updateListeDeTache(@PathVariable Long id, @RequestBody ListeDeTache updatedListeDeTache) {
        return ResponseEntity.ok(listeDeTachePort.updateListeDeTache(
            id,
            updatedListeDeTache.getNom(),
            updatedListeDeTache.getDate(),
            updatedListeDeTache.getEtat()
        ));
    }

    @Operation(summary = "Supprimer une liste de tâches")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteListeDeTache(@PathVariable Long id) {
        listeDeTachePort.deleteListeDeTache(id);
        return ResponseEntity.noContent().build();
    }
}