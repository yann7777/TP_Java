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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;





@RestController
@RequestMapping("/taches")
@CrossOrigin(origins = "**")
public class TacheController {

    private final TacheUseCase tachePort;
    private final CustomUserDetailsService userDetailsService;
    private final ProjetUseCase projetUseCase; 


    public TacheController(TacheUseCase tachePort, CustomUserDetailsService userDetailsService, ProjetUseCase projetUseCase) {
        this.tachePort = tachePort;
        this.userDetailsService = userDetailsService;
        this.projetUseCase = projetUseCase;
    }

    @GetMapping("/mes-taches")
    public ResponseEntity<List<Tache>> getTachesByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Long userId = userDetailsService.findUserIdByEmail(username);

        List<Tache> taches = tachePort.getTachesByUserId(userId);
        return ResponseEntity.ok(taches);
    }

    @PostMapping
    public ResponseEntity<Tache> createTache(@RequestBody Tache tache) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Refuser l'accès si non authentifié
            }
    
            String username = authentication.getName();
    
            Long userId = userDetailsService.findUserIdByEmail(username);
    
            Tache createdTache = tachePort.createTache(
                tache.getTitre(),
                tache.getDescription(),
                tache.getEtat(),
                userId, 
                tache.getIdProjet(), 
                tache.getDateRappel(),
                tache.isPinned(),
                tache.isArchived()
            );
    
            System.out.println("Tâche créée : " + createdTache);
    
            return ResponseEntity.ok(createdTache);
        } catch (Exception e) {
            System.err.println("Erreur lors de la création de la tâche : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/projets")
    public ResponseEntity<List<Projet>> getProjetsByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Long userId = userDetailsService.findUserIdByEmail(username);

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
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    Long userId = userDetailsService.findUserIdByEmail(username);
    
    try {
        Tache tache = tachePort.updateTache(id, updatedTache.getTitre(), updatedTache.getDescription(), updatedTache.getEtat(), updatedTache.getDateRappel(), userId);
        return ResponseEntity.ok(tache);
    } catch (RuntimeException e) { return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); }
}


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTache(@PathVariable Long id) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

            Long userId = userDetailsService.findUserIdByEmail(username);
        
        try {
            tachePort.deleteTache(id, userId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/rappels")
    public ResponseEntity<List<Tache>> getTachesAvecRappels() {
        LocalDateTime maintenant = LocalDateTime.now();
        List<Tache> taches = tachePort.getAllTaches();

        List<Tache> tachesAvecRappels = taches.stream()
            .filter(tache -> tache.getDateRappel() != null && tache.getDateRappel().isBefore(maintenant))
            .collect(Collectors.toList());

        return ResponseEntity.ok(tachesAvecRappels);
    }

    @PostMapping("/{id}/pin")
    public ResponseEntity<Tache> pinTache(@PathVariable Long id) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Long userId = userDetailsService.findUserIdByEmail(username);

        Tache tache = tachePort.pinTache(id, userId);
        return ResponseEntity.ok(tache);
    }

    @PostMapping("/{id}/unpin")
    public ResponseEntity<Tache> unpinTache(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Long userId = userDetailsService.findUserIdByEmail(username);

        Tache tache = tachePort.unpinTache(id, userId);
        return ResponseEntity.ok(tache);
    }


    @GetMapping("/rechercher")
    public ResponseEntity<List<Tache>> rechercherTaches(@RequestParam(required = false) String terme) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Long userId = userDetailsService.findUserIdByEmail(username);
        List<Tache> taches = tachePort.rechercherTaches(userId, terme);
        if (taches.isEmpty()) {
            return ResponseEntity.noContent().build();
        } 

        return ResponseEntity.ok(taches);
    }

    
    @PostMapping("/{id}/assign")
    public ResponseEntity<Tache> assignTache(@PathVariable Long id, @RequestParam Long assigneeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Long currentUserId = userDetailsService.findUserIdByEmail(username);

        try {
            Tache tache = tachePort.assignTache(id, assigneeId, currentUserId);
            return ResponseEntity.ok(tache);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    @PostMapping("/{id}/unassign")
    public ResponseEntity<Tache> unassignTache(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Long currentUserId = userDetailsService.findUserIdByEmail(username);

        try {
            Tache tache = tachePort.unassignTache(id, currentUserId);
            return ResponseEntity.ok(tache);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }
    
    

    @GetMapping("/assigned-to-me")
    public ResponseEntity<List<Tache>> getTacheAssignedToMe() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Long userId = userDetailsService.findUserIdByEmail(username);

        List<Tache> taches = tachePort.getAssignedTaches(userId);

        return ResponseEntity.ok(taches);
    }
    

    @PostMapping("/{id}/archive")
    public ResponseEntity<Tache> archiveTache(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        String username = authentication.getName();
        Long userId = userDetailsService.findUserIdByEmail(username);

        try {
            Tache tache = tachePort.archivedTache(id, userId);
            return ResponseEntity.ok(tache);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
    }

    @PostMapping("/{id}/unarchive")
    public ResponseEntity<Tache> unarchiveTache(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Long userId = userDetailsService.findUserIdByEmail(username);

        try {
            Tache tache = tachePort.unarchivedTache(id, userId);
            return ResponseEntity.ok(tache);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
    }


    @GetMapping("/archived")
    public ResponseEntity<List<Tache>> getArchiveTaches() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Long userId = userDetailsService.findUserIdByEmail(username);

        List<Tache> taches = tachePort.getArchivedTaches(userId);
        return ResponseEntity.ok(taches);
    }
    
    
    
}