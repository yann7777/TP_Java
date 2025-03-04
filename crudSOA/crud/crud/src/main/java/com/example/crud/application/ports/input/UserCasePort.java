package com.example.crud.application.ports.input;

import com.example.crud.domain.model.User;
import java.util.List;
import java.util.Optional;

public interface UserCasePort {
    User createUser(String nom, String prenom, String email, String password);
    Optional<User> getUser(Long id);
    List<User> getAllUsers();
    User saveUser(User user);
    User updateUser(Long id, String nom, String prenom, String email, String password);
    void deleteUser(Long id);
}
