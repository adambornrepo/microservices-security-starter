package ink.th.auth.payload.response;

import java.io.Serializable;

public record UserSaveResponse(
        String id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber
) implements Serializable {

}
