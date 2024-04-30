package ink.th.auth.payload.response;

import java.io.Serializable;

public record LoginResponse(
        String id,
        String email,
        String token
) implements Serializable {

}
