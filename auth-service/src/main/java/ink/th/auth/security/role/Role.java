package ink.th.auth.security.role;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    ADMIN(
            Set.of(
                    Permission.ADMIN,
                    Permission.MANAGER,
                    Permission.CUSTOMER
            )
    ),
    MANAGER(
            Set.of(
                    Permission.MANAGER,
                    Permission.CUSTOMER
            )
    ),
    CUSTOMER(
            Set.of(
                    Permission.CUSTOMER
            )
    );

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    /**
     * Returns the list of authorities for the current user.
     *
     * @return the list of authorities
     */
    public List<SimpleGrantedAuthority> getAuthorities() {

        // Get the list of permissions and map them to SimpleGrantedAuthority objects
        return getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
    }

}
