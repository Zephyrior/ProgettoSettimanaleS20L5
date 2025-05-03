package it.epicode.ProgettoSettimanaleS20L5.auth;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
}
