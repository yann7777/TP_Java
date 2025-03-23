package todolist.create.list.infrasctructure.adapters.input.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import todolist.create.list.application.ports.input.NoteUseCase;
import todolist.create.list.application.services.CustomUserDetailsService;
import todolist.create.list.domain.model.Note;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notes")
@CrossOrigin(origins = "**")
public class NoteController {

    private final NoteUseCase notePort;
    private final CustomUserDetailsService userDetailsService;

    public NoteController(NoteUseCase notePort, CustomUserDetailsService userDetailsService) {
        this.notePort = notePort;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/mes-notes")
    public ResponseEntity<List<Note>> getNotesByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Long userId = userDetailsService.findUserIdByEmail(username);

        List<Note> notes = notePort.getNotesByUserId(userId);
        return ResponseEntity.ok(notes);
    }

    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody Note note) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            String username = authentication.getName();
            Long userId = userDetailsService.findUserIdByEmail(username);

            Note createdNote = notePort.createNote(
                note.getContent(),
                userId
            );

            System.out.println("Note créée : " + createdNote);

            return ResponseEntity.ok(createdNote);
        } catch (Exception e) {
            System.err.println("Erreur lors de la création de la note : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNote(@PathVariable Long id) {
        Optional<Note> note = notePort.getNote(id);
        return note.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes() {
        return ResponseEntity.ok(notePort.getAllNotes());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable Long id, @RequestBody Note updatedNote) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Long userId = userDetailsService.findUserIdByEmail(username);
        
        try {
            Note note = notePort.updateNote(id, updatedNote.getContent(), userId);
            return ResponseEntity.ok(note);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); 
            }

            String username = authentication.getName(); 
            Long userId = userDetailsService.findUserIdByEmail(username);
            notePort.deleteNote(id, userId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
    

}