package ink.th.user.controller;

import ink.th.user.payload.request.UserSaveRequest;
import ink.th.user.payload.resource.UserDetailsResource;
import ink.th.user.payload.response.UserSaveResponse;
import ink.th.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/save")
    public ResponseEntity<UserSaveResponse> userSave(@RequestBody @Valid UserSaveRequest request) {
        return ResponseEntity.ok(userService.saveUser(request));
    }

    @GetMapping("/get/one/email/{email}")
    public ResponseEntity<UserDetailsResource> getOneUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getOneUserByEmail(email));
    }

    @GetMapping("/protected")
    public ResponseEntity<String> getProtected() {
        return ResponseEntity.ok("I am protected");
    }

}
