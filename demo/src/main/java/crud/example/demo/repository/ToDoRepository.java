package crud.example.demo.repository;

import crud.example.demo.model.ToDo;
import jakarta.websocket.Decoder.Text;

import org.springframework.data.jpa.repository.JpaRepository;

//import org.springframework.data.mongodb.repository.MongoRepository;


public interface ToDoRepository extends JpaRepository<ToDo, Long>{
    boolean existsByTitre(String titre);
}
