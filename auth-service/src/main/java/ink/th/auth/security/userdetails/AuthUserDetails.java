package ink.th.auth.security.userdetails;

import ink.th.auth.security.role.Role;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Builder
public class AuthUserDetails implements UserDetails {

    private final String id;
    private final String email;
    private final String passwordHash;
    private final Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRole().getAuthorities();
    }

    @Override
    public String getPassword() {
        return this.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
