package crud.example.demo.controller;

import crud.example.demo.model.ToDo;
import crud.example.demo.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

@RestController
@RequestMapping("/lister")
@CrossOrigin(origins = "*")
public class ToDoController {
    @Autowired
    private ToDoService toDoService;

    @Operation(summary = "Récupérer toutes les tâches", description = "Retourne la liste des tâches avec pagination")
    @GetMapping
    public List<ToDo> getAllTasks(Pageable pageable) {
        return toDoService.getAllToDos(pageable).getContent();
    }

    @Operation(summary = "Récupérer une tâche par ID", description = "Retourne une tâche spécifique par son ID")
    @GetMapping("/{id}")
    public ResponseEntity<ToDo> getTaskById(@PathVariable String id) {
        return ResponseEntity.ok(toDoService.getToDoById(id));
    }

    //@Operation(summary = "Créer une nouvelle tâche", description = "Permet de créer une nouvelle tâche")
    @PostMapping
    public ResponseEntity<ToDo> createTask(@RequestBody @Validated ToDo toDo) {
        ToDo createdToDo = toDoService.createToDo(toDo);
        return new ResponseEntity<>(createdToDo, HttpStatus.CREATED);
    }

    @Operation(summary = "Mettre à jour une tâche", description = "Met à jour les détails d'une tâche existante")
    @PutMapping("/{id}")
    public ResponseEntity<ToDo> updateTask(@PathVariable String id, @RequestBody ToDo toDo) {
        ToDo updatedToDo = toDoService.updateToDo(id, toDo);
        return ResponseEntity.ok(updatedToDo);
    }

    @Operation(summary = "Supprimer une tâche", description = "Supprime une tâche spécifique par son ID")
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable String id) {
        toDoService.deleteToDo(id);
    }
}
