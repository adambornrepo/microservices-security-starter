package ink.th.user.payload.mapper;

import ink.th.user.domain.entity.User;
import ink.th.user.payload.request.UserSaveRequest;
import ink.th.user.payload.resource.UserDetailsResource;
import ink.th.user.payload.response.UserSaveResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserSaveResponse toUserSaveResponse(User user) {
        return new UserSaveResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber()
        );
    }

    public UserDetailsResource toUserDetailsResource(User user) {
        return new UserDetailsResource(
                user.getId(),
                user.getEmail(),
                user.getPasswordHash(),
                user.getRole()
        );
    }

    public User toUser(UserSaveRequest request) {
        return User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .phoneNumber(request.phoneNumber())
                .passwordHash(request.password())
                .build();
    }

}
