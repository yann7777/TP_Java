package crud.example.demo.repository;

import crud.example.demo.model.ToDo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ToDoRepository extends MongoRepository<ToDo, String> {
    boolean existsByTitre(String titre);
    Optional<ToDo> findById(String id);  // MongoDB utilise String pour l'id
    boolean existsById(String id); // MongoDB utilise String pour l'id
    void deleteById(String id); // MongoDB utilise String pour l'id
}
