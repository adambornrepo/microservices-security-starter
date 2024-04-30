package ink.th.auth.payload.mapper;

import ink.th.auth.payload.resource.UserDetailsResource;
import ink.th.auth.payload.response.LoginResponse;
import ink.th.auth.security.userdetails.AuthUserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public AuthUserDetails toAuthUserDetails(UserDetailsResource userDetailsResource) {
        return AuthUserDetails.builder()
                .id(userDetailsResource.id())
                .email(userDetailsResource.username())
                .passwordHash(userDetailsResource.password())
                .role(userDetailsResource.role())
                .build();
    }

    public LoginResponse toLoginResponse(AuthUserDetails userDetails, String jwtToken) {
        return new LoginResponse(
                userDetails.getId(),
                userDetails.getEmail(),
                jwtToken);
    }

}
