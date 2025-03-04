package com.example.crud.infrastructure.adapters.input.rest;

public class AuthRequest {
    private String email; // Remplacez "username" par "email"
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}