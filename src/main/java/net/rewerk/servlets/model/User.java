package net.rewerk.servlets.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.rewerk.servlets.enumerator.UserRole;

import java.util.UUID;
@Data
@EqualsAndHashCode
@AllArgsConstructor
public class User {
    private UUID id;
    private String username;
    private String password;
    private UserRole role;

    public boolean isAdmin() {
        return role.equals(UserRole.ADMIN);
    }
}
