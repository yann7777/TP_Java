package com.example.crud.infrastructure.adapters.input.graphql;

import com.example.crud.application.ports.input.UserCasePort;
import com.example.crud.domain.exceptions.GraphQLException;
import com.example.crud.domain.model.User;
import org.springframework.stereotype.Component;
import graphql.kickstart.tools.GraphQLMutationResolver;

@Component
public class UserMutationResolver implements GraphQLMutationResolver {

    private final UserCasePort userCasePort;

    public UserMutationResolver(UserCasePort userCasePort) {
        this.userCasePort = userCasePort;
    }

    public User createUser(String nom, String prenom, String email, String password) {
        User user = userCasePort.createUser(nom, prenom, email, password);
        if (user == null) {
            throw new GraphQLException("Failed to create user: UserCasePort returned null");
        }
        return user;
    }

    public User updateUser(Long id, String nom, String prenom, String email, String password) {
        User user = userCasePort.updateUser(id, nom, prenom, email, password);
        if (user == null) {
            throw new GraphQLException("Failed to update user: UserCasePort returned null");
        }
        return user;
    }

    public boolean deleteUser(Long id) {
        try {
            userCasePort.deleteUser(id);
            return true;
        } catch (Exception e) {
            throw new GraphQLException("Failed to delete user: " + e.getMessage());
        }
    }
}