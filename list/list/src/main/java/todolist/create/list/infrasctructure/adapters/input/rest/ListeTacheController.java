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

import todolist.create.list.application.ports.input.ListeTacheUseCase;
import todolist.create.list.application.services.CustomUserDetailsService;
import todolist.create.list.domain.model.ListeTache;

@RestController
@RequestMapping("/listetaches")
@CrossOrigin(origins = "**")
public class ListeTacheController {
    
     private final ListeTacheUseCase listeTachePort;
    private final CustomUserDetailsService userDetailsService;

    public ListeTacheController(ListeTacheUseCase listeTachePort, CustomUserDetailsService userDetailsService) {
        this.listeTachePort = listeTachePort;
        this.userDetailsService = userDetailsService;
    }


    @PostMapping
    public ResponseEntity<ListeTache> createListeTache(@RequestBody ListeTache listeTache) {
        try {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Refuser l'accès si non authentifié
            }

        String username = authentication.getName(); // Récupère l'email de l'utilisateur
        Long userId = userDetailsService.findUserIdByEmail(username);

        ListeTache createdListeTache = listeTachePort.createListeTache(
            listeTache.getDescription(),
            listeTache.getEtat(),
            listeTache.getIdProjet(),
            listeTache.getIdTache(),
            userId 
        );

        System.out.println("Liste de tâches créée : " + createdListeTache);

        return ResponseEntity.ok(createdListeTache);
    } catch (Exception e) {
        System.err.println("Erreur lors de la création de la tâche : " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}


    @GetMapping("/mes-listetaches")
    public ResponseEntity<List<ListeTache>> getListeTachesByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Récupère l'email de l'utilisateur

        Long userId = userDetailsService.findUserIdByEmail(username);

        List<ListeTache> listeTaches = listeTachePort.getListTachesByUserId(userId);
        return ResponseEntity.ok(listeTaches);
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
