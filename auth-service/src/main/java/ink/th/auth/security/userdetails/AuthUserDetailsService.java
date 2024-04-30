package ink.th.auth.security.userdetails;

import ink.th.auth.client.UserServiceClient;
import ink.th.auth.payload.mapper.AuthMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUserDetailsService implements UserDetailsService {

    private final UserServiceClient userServiceClient;
    private final AuthMapper authMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userDetailsResource = userServiceClient.getOneUserByEmail(username).getBody();
        return authMapper.toAuthUserDetails(userDetailsResource);
    }

}
