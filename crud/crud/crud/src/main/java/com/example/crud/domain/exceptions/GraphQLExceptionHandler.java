package com.example.crud.domain.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GraphQLExceptionHandler {

    @ExceptionHandler(GraphQLException.class)
    public Map<String, Object> handleGraphQLException(GraphQLException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("message", ex.getMessage());
        error.put("classification", "DataFetchingException");
        return error;
    }
}