package it.epicode.ProgettoSettimanaleS20L5.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserResponse {

    private Long id;
    private String username;
    private Set<Role> role;
}
