package crud.example.demo.controller;

import crud.example.demo.model.ToDo;
import crud.example.demo.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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
    

    @GetMapping
    public List<ToDo> getAllTasks(Pageable pageable) {
        return toDoService.getAllToDos(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(toDoService.getToDoById(id));
    }

    @PostMapping
    public ResponseEntity<ToDo> createTask(@RequestBody @Validated ToDo toDo) {
        ToDo createdToDo = toDoService.createToDo(toDo);
        return new ResponseEntity<>(createdToDo, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ToDo> updateTask(@PathVariable Long id, @RequestBody ToDo toDo) {
        ToDo updatedToDo = toDoService.updateToDo(id, toDo);
        return ResponseEntity.ok(updatedToDo);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ToDo> updateTache(@PathVariable Long id, @Validated ToDo toDo){
        ToDo updateTache = toDoService.updateToDo(id, toDo);
        return ResponseEntity.ok(updateTache);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        toDoService.deleteToDo(id);
    }

}
