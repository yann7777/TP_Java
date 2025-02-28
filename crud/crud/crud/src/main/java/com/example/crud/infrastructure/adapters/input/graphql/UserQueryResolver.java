package com.example.crud.infrastructure.adapters.input.graphql;

import com.example.crud.application.ports.input.UserCasePort;
import com.example.crud.domain.model.User;
import org.springframework.stereotype.Component;
import graphql.kickstart.tools.GraphQLQueryResolver;
import java.util.List;
import java.util.Optional;

@Component
public class UserQueryResolver implements GraphQLQueryResolver {

    private final UserCasePort userCasePort;

    public UserQueryResolver(UserCasePort userCasePort) {
        this.userCasePort = userCasePort;
    }

    public Optional<User> getUser(Long id) {
        return userCasePort.getUser(id);
    }

    public List<User> getAllUsers() {
        return userCasePort.getAllUsers();
    }
}