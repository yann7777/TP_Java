package crud.example.demo.controller;

import crud.example.demo.model.ToDo;
import crud.example.demo.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

//import java.util.HashMap;
import java.util.List;
//import java.util.Optional;

@RestController
@RequestMapping("/lister")
@CrossOrigin(origins = "*")
public class ToDoController {
    @Autowired
    private ToDoService toDoService;

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }

    @Operation(summary = "Récupérer toutes les tâches", description = "Retourne la liste des tâches avec une pagination")    
    @GetMapping
    public List<ToDo> getAllTasks(Pageable pageable) {
        return (List<ToDo>) toDoService.getAllToDos(pageable);
    }

    @Operation(summary = "Récupérer une tâche par ID", description = "Retourne la liste des tâches avec une pagination")
    @GetMapping("/{id}")
    public ResponseEntity getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(toDoService.getToDoById(id));
    }

    @Operation(summary = "Créer une tâche par ID", description = "Retourne la liste la tâche créée")
    @PostMapping
    public ResponseEntity<ToDo> createTask(@RequestBody @Validated ToDo toDo) {
        ToDo createdToDo = toDoService.createToDo(toDo);
        return new ResponseEntity<>(createdToDo, HttpStatus.CREATED);
    }
    
    @Operation(summary = "Modifier une tâche par ID", description = "Retourne la liste la tâche")
    @PutMapping("/{id}")
    public ResponseEntity<ToDo> updateTask(@PathVariable Long id, @RequestBody ToDo toDo) {
        ToDo updatedToDo = toDoService.updateToDo(id, toDo);
        return ResponseEntity.ok(updatedToDo);
    }

    @Operation(summary = "Modifier une tâche par ID", description = "Retourne la liste la tâche modifier")
    @PatchMapping("/{id}")
    public ResponseEntity<ToDo> updateTache(@PathVariable Long id, @Validated ToDo toDo){
        ToDo updateTache = toDoService.updateToDo(id, toDo);
        return ResponseEntity.ok(updateTache);
    }

    @Operation(summary = "Supprimer une tâche par ID", description = "Retourne la tâche supprimée")
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        toDoService.deleteToDo(id);
    }

}
