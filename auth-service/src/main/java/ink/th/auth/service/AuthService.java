package ink.th.auth.service;

import ink.th.auth.client.UserServiceClient;
import ink.th.auth.payload.mapper.AuthMapper;
import ink.th.auth.payload.request.LoginRequest;
import ink.th.auth.payload.request.UserSaveRequest;
import ink.th.auth.payload.response.LoginResponse;
import ink.th.auth.payload.response.UserSaveResponse;
import ink.th.auth.security.service.TokenService;
import ink.th.auth.security.userdetails.AuthUserDetails;
import ink.th.auth.security.userdetails.AuthUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthUserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final UserServiceClient useServiceClient;
    private final TokenService tokenService;
    private final AuthMapper authMapper;

    public UserSaveResponse signUp(UserSaveRequest request) {
        return useServiceClient.userSave(request).getBody();
    }

    @Transactional
    public LoginResponse login(LoginRequest request) {
        // Authenticate the user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        // Get user details
        var userDetails = (AuthUserDetails) userDetailsService.loadUserByUsername(request.email());
        // Generate JWT token
        var jwtToken = tokenService.generateToken(userDetails);
        // Map user and JWT token to login response
        return authMapper.toLoginResponse(userDetails, jwtToken);
    }

}
