package com.example.crud.domain.exceptions;

public class GraphQLException extends RuntimeException {
    public GraphQLException(String message) {
        super(message);
    }
}