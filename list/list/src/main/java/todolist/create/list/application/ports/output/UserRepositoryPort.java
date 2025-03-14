package todolist.create.list.application.ports.output;

import java.util.List;
import java.util.Optional;

import todolist.create.list.domain.model.User;

public interface UserRepositoryPort {
    User save(User user);
    Optional<User> findById(Long id);
    List<User> findAll();
    void deleteById(Long id);
}
