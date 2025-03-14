package todolist.create.list.application.ports.output;

import java.util.List;
import java.util.Optional;

import todolist.create.list.domain.model.Projet;

public interface ProjetRepositoryPort {
    Projet save(Projet projet);
    Optional<Projet> findById(Long id);
    List<Projet> findAll();
    void deleteById(Long id);
}
