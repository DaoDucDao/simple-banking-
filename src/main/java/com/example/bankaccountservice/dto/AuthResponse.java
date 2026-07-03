package com.example.bankaccountservice.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthResponse {
    private String token;
    private String email;
    private String username;

    public AuthResponse(String token, String username, String email) {
        this.token = token;
        this.email = email;
        this.username = username;
    }
}
