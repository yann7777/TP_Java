package todolist.create.list.application.ports.output;

import java.util.List;
import java.util.Optional;

import todolist.create.list.domain.model.ListeTache;

public interface ListeTacheRepositoryPort {
    ListeTache save(ListeTache listeTache);
    Optional<ListeTache> findById(Long id);
    List<ListeTache> findAll();
    void deleteById(Long id);
}
