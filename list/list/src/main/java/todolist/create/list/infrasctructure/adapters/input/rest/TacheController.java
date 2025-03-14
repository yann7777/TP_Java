package todolist.create.list.infrasctructure.adapters.input.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import todolist.create.list.application.ports.input.TacheUseCase;
import todolist.create.list.domain.model.Tache;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/taches")
@CrossOrigin(origins = "*")
public class TacheController {

    private final TacheUseCase tachePort;

    public TacheController(TacheUseCase tachePort) {
        this.tachePort = tachePort;
    }

    @PostMapping
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