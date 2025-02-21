package crud.example.demo.repository;

import crud.example.demo.model.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ToDoRepository extends JpaRepository<ToDo, Long>{
    boolean existsByTitre(String titre);
}
