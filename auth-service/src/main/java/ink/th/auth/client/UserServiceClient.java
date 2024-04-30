package ink.th.auth.client;


import ink.th.auth.payload.request.UserSaveRequest;
import ink.th.auth.payload.resource.UserDetailsResource;
import ink.th.auth.payload.response.UserSaveResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", path = "/v1/users")
public interface UserServiceClient {

    @PostMapping("/save")
    ResponseEntity<UserSaveResponse> userSave(@RequestBody @Valid UserSaveRequest request);

    @GetMapping("/get/one/email/{email}")
    ResponseEntity<UserDetailsResource> getOneUserByEmail(@PathVariable String email);

}
