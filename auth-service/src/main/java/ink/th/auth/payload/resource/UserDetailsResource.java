package ink.th.auth.payload.resource;

import ink.th.auth.security.role.Role;

import java.io.Serializable;

public record UserDetailsResource(
        String id,
        String username,
        String password,
        Role role
) implements Serializable {

}
