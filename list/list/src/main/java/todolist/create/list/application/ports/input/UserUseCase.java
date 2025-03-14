package todolist.create.list.application.ports.input;

import java.util.List;
import java.util.Optional;

import todolist.create.list.domain.model.User;

public interface UserUseCase {
    User createUser(String prenom, String nom, String email, String password);
    Optional<User> getUser(Long id);
    List<User> getAllUsers();
    User saveUser(User user);
    User updateUser(Long id, String prenom, String nom, String email, String password);
    void deleteUser(Long id);
    void registerUser(User user);
}
