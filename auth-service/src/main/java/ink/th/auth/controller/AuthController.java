package ink.th.auth.controller;

import ink.th.auth.payload.request.LoginRequest;
import ink.th.auth.payload.request.UserSaveRequest;
import ink.th.auth.payload.response.LoginResponse;
import ink.th.auth.payload.response.UserSaveResponse;
import ink.th.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/signup")
    public ResponseEntity<UserSaveResponse> signUp(@RequestBody UserSaveRequest request) {
        return ResponseEntity.ok(authService.signUp(request));
    }
}
