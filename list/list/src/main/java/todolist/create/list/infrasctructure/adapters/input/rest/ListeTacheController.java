package todolist.create.list.infrasctructure.adapters.input.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import todolist.create.list.application.ports.input.ListeTacheUseCase;
import todolist.create.list.domain.model.ListeTache;

@RestController
@RequestMapping("/listetaches")
@CrossOrigin(origins = "*")
public class ListeTacheController {
    
     private final ListeTacheUseCase listeTachePort;

    public ListeTacheController(ListeTacheUseCase listeTachePort) {
        this.listeTachePort = listeTachePort;
    }

    @PostMapping
    public ResponseEntity<ListeTache> createListeTache(@RequestBody ListeTache listeTache) {
        ListeTache createdListeTache = listeTachePort.createListeTache(
            listeTache.getDescription(),
            listeTache.getEtat(),
            listeTache.getIdProjet(),  // Utilisez idProjet
            listeTache.getIdTache(),   // Utilisez idTache
            listeTache.getIdUser()     // Utilisez idUser
        );
        return ResponseEntity.ok(createdListeTache);
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<ListeTache> getListeTache(@PathVariable Long id) {
        Optional<ListeTache> listeDeTache = listeTachePort.getListeTache(id);
        return listeDeTache.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ListeTache>> getAllListeDeTaches() {
        return ResponseEntity.ok(listeTachePort.getAllListeTaches());
    }


    @GetMapping("/user/{idUser}")
    public ResponseEntity<List<ListeTache>> getListTachesByUserId(@PathVariable Long idUser) {
        return ResponseEntity.ok(listeTachePort.getListTachesByUserId(idUser));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ListeTache> updateListeTache(@PathVariable Long id, @RequestBody ListeTache updatedListeTache) {
        return ResponseEntity.ok(listeTachePort.updateListeTache(
            id,
            updatedListeTache.getDescription(),
            updatedListeTache.getEtat()
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteListeTache(@PathVariable Long id) {
        listeTachePort.deleteListeTache(id);
        return ResponseEntity.noContent().build();
    }
}
