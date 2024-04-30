package ink.th.user.service;

import ink.th.common.enums.LogLevel;
import ink.th.common.requests.LogRequest;
import ink.th.user.client.LogServiceClient;
import ink.th.user.domain.entity.User;
import ink.th.user.domain.enums.Role;
import ink.th.user.exception.customs.ConflictException;
import ink.th.user.exception.customs.UserNotFoundException;
import ink.th.user.payload.mapper.UserMapper;
import ink.th.user.payload.request.UserSaveRequest;
import ink.th.user.payload.resource.UserDetailsResource;
import ink.th.user.payload.response.UserSaveResponse;
import ink.th.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final LogServiceClient logServiceClient;


    public UserSaveResponse saveUser(UserSaveRequest request) {
        User user = userMapper.toUser(request);
        // Check for duplicate fields
        checkDuplicate(user.getEmail(), user.getPhoneNumber());
        // Hash the user's password
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        // Set the user's role to CUSTOMER
        user.setRole(Role.CUSTOMER);
        // Save the user to the database
        User saved = userRepository.save(user);
        // Log the user creation
        logServiceClient.createLog(new LogRequest(
                "User created : " + saved,
                "/v1/users/save",
                "user-service",
                LogLevel.INFO
        ));
        // Return the saved user
        return userMapper.toUserSaveResponse(saved);
    }

    private void checkDuplicate(String email, String phoneNumber) {
        boolean isEmailExist = userRepository.existsByEmail(email);
        if (isEmailExist) {
            throw new ConflictException("Email already exists : " + email);
        }

        boolean isPhoneNumberExist = userRepository.existsByPhoneNumber(phoneNumber);
        if (isPhoneNumberExist) {
            throw new ConflictException("Phone number already exists : " + phoneNumber);
        }
    }

    public UserDetailsResource getOneUserByEmail(String email) {
        User oneUserByEmail = findOneUserByEmail(email);
        return userMapper.toUserDetailsResource(oneUserByEmail);
    }

    private User findOneUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found : " + email));
    }

}
