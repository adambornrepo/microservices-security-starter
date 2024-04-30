package ink.th.user.repository;


import ink.th.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByEmail(@NonNull String email);

    boolean existsByPhoneNumber(@NonNull String phoneNumber);

    Optional<User> findByEmail(@NonNull String email);
}
