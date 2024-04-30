package ink.th.user.payload.resource;

import ink.th.user.domain.enums.Role;

import java.io.Serializable;


public record UserDetailsResource(
        String id,
        String username,
        String password,
        Role role
) implements Serializable {

}
