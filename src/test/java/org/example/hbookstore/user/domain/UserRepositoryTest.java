package org.example.hbookstore.user.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.Optional;
import org.example.hbookstore.shared.AbstractDataJdbcTest;
import org.example.hbookstore.user.domain.enums.UserRole;
import org.example.hbookstore.user.domain.enums.UserStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("UserRepository Integration Tests")
class UserRepositoryTest extends AbstractDataJdbcTest {

    @Autowired private UserRepository userRepository;

    @Test
    void save_persistsUser_andGeneratesId() {
        User user =
                new User(
                        null,
                        "novak",
                        "hashed-secret",
                        UserRole.USER,
                        UserStatus.ACTIVE,
                        Instant.now(),
                        Instant.now());

        User saved = userRepository.save(user);

        assertThat(saved.getId()).isNotNull();
    }

    @Test
    void findById_returnsPersistedUser() {
        User saved =
                userRepository.save(
                        new User(
                                null,
                                "novak",
                                "hashed-secret",
                                UserRole.USER,
                                UserStatus.ACTIVE,
                                Instant.now(),
                                Instant.now()));

        Optional<User> found = userRepository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getUsername()).isEqualTo("novak");
        assertThat(found.get().getRole()).isEqualTo(UserRole.USER);
    }

    @Test
    void findById_returnsEmpty_whenNotFound() {
        Optional<User> found = userRepository.findById(999_999L);

        assertThat(found).isEmpty();
    }

    @Test
    void update_persistsChanges() {
        User saved =
                userRepository.save(
                        new User(
                                null,
                                "novak",
                                "hashed-secret",
                                UserRole.USER,
                                UserStatus.ACTIVE,
                                Instant.now(),
                                Instant.now()));

        saved.setStatus(UserStatus.INACTIVE);
        userRepository.save(saved);

        User reloaded = userRepository.findById(saved.getId()).orElseThrow();
        assertThat(reloaded.getStatus()).isEqualTo(UserStatus.INACTIVE);
    }

    @Test
    void deleteById_removesUser() {
        User saved =
                userRepository.save(
                        new User(
                                null,
                                "novak",
                                "hashed-secret",
                                UserRole.USER,
                                UserStatus.ACTIVE,
                                Instant.now(),
                                Instant.now()));

        userRepository.deleteById(saved.getId());

        assertThat(userRepository.findById(saved.getId())).isEmpty();
    }

    @Test
    void findByUsername_returnsMatchingUser() {
        userRepository.save(
                new User(
                        null,
                        "novak",
                        "hashed-secret",
                        UserRole.USER,
                        UserStatus.ACTIVE,
                        Instant.now(),
                        Instant.now()));

        Optional<User> found = userRepository.findByUsername("novak");

        assertThat(found).isPresent();
        assertThat(found.get().getUsername()).isEqualTo("novak");
    }

    @Test
    void findByUsername_returnsEmpty_whenNotFound() {
        Optional<User> found = userRepository.findByUsername("nonexistent");

        assertThat(found).isEmpty();
    }

    @Test
    void existsByUsername_returnsTrue_whenUsernameTaken() {
        userRepository.save(
                new User(
                        null,
                        "novak",
                        "hashed-secret",
                        UserRole.USER,
                        UserStatus.ACTIVE,
                        Instant.now(),
                        Instant.now()));

        assertThat(userRepository.existsByUsername("novak")).isTrue();
    }

    @Test
    void existsByUsername_returnsFalse_whenUsernameFree() {
        assertThat(userRepository.existsByUsername("nobody")).isFalse();
    }
}
