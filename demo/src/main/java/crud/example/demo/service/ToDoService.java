package crud.example.demo.service;

import crud.example.demo.model.ToDo;
import crud.example.demo.repository.ToDoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@Service
public class ToDoService {
    @Autowired
    private ToDoRepository toDoRepository;

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }
    
    public List<ToDo> getAllToDos(Pageable pageable) {
        return toDoRepository.findAll();
    }

    public ResponseEntity getToDoById(Long id){
        return ResponseEntity.ok(toDoRepository.findById(id));
    }
    
    public ToDo createToDo(ToDo toDo) {
        if (toDoRepository.existsByTitre(toDo.getTitre())) {
            throw new DuplicateKeyException("La tâche avec ce titre existe déjà");
        }
        return toDoRepository.save(toDo);
    }
    
    public ToDo updateToDo(Long id, ToDo updateToDo){
        return toDoRepository.findById(id).map(toDo -> {
            toDo.setTitre(updateToDo.getTitre());
            toDo.setStatus(updateToDo.isStatus());
            return toDoRepository.save(toDo);
        }).orElseThrow(() -> new ResourceNotFoundException("La tâche n'a pas été trouvée avec l'id" + id));
    }
    
    public void deleteToDo(Long id) {
        if (!toDoRepository.existsById(id)) {
            throw new ResourceNotFoundException("La tâche n'a pas été trouvée avec l'id" + id);
        }
        toDoRepository.deleteById(id);
    }
    
}
