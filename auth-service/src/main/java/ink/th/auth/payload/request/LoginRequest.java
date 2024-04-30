package ink.th.auth.payload.request;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record LoginRequest(
        @NotBlank(message = "Email cannot be blank")
        String email,
        @NotBlank(message = "Password cannot be blank")
        String password
) implements Serializable {

}
