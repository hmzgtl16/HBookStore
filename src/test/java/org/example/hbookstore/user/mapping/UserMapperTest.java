package org.example.hbookstore.user.mapping;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import org.example.hbookstore.user.api.dto.CreateUserRequest;
import org.example.hbookstore.user.api.dto.UpdateUserRequest;
import org.example.hbookstore.user.api.dto.UserResponse;
import org.example.hbookstore.user.domain.User;
import org.example.hbookstore.user.domain.enums.UserRole;
import org.example.hbookstore.user.domain.enums.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserMapperTest {

    private UserMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new UserMapperImpl();
    }

    @Test
    void toEntity_mapsFieldsAndDefaultsStatusToActive_withNullIdAndBlankPassword() {
        CreateUserRequest request = new CreateUserRequest("alovelace", "", UserRole.USER);
        User user = mapper.toEntity(request);

        assertThat(user.getId()).isNull();
        assertThat(user.getUsername()).isEqualTo("alovelace");
        assertThat(user.getPassword()).isEmpty(); // set later by the service, after hashing
        assertThat(user.getRole()).isEqualTo(UserRole.USER);
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE); // always ACTIVE on create
        assertThat(user.getCreatedAt()).isNotNull().isBeforeOrEqualTo(Instant.now());
        assertThat(user.getUpdatedAt()).isNotNull().isBeforeOrEqualTo(Instant.now());
    }

    @Test
    void updateEntity_appliesOnlyNonNullFields_andPreservesOthers() {
        Instant originalCreatedAt = Instant.now().minusSeconds(3600);
        User user =
                new User(
                        1L,
                        "alovelace",
                        "hashed-secret",
                        UserRole.USER,
                        UserStatus.ACTIVE,
                        originalCreatedAt,
                        originalCreatedAt);

        UpdateUserRequest request = new UpdateUserRequest(null, UserRole.ADMIN, null);

        mapper.updateEntity(user, request);

        assertThat(user.getRole()).isEqualTo(UserRole.ADMIN); // updated
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE); // preserved
        assertThat(user.getUsername()).isEqualTo("alovelace"); // never touched by update
        assertThat(user.getPassword()).isEqualTo("hashed-secret"); // never touched by update
        assertThat(user.getCreatedAt()).isEqualTo(originalCreatedAt); // untouched
    }

    @Test
    void updateEntity_updatesStatusWhenProvided() {
        User user =
                new User(
                        1L,
                        "alovelace",
                        "hashed-secret",
                        UserRole.USER,
                        UserStatus.ACTIVE,
                        Instant.now(),
                        Instant.now());

        UpdateUserRequest request = new UpdateUserRequest(null, null, UserStatus.INACTIVE);

        mapper.updateEntity(user, request);

        assertThat(user.getStatus()).isEqualTo(UserStatus.INACTIVE);
        assertThat(user.getRole()).isEqualTo(UserRole.USER); // preserved
    }

    @Test
    void updateEntity_alwaysTouchesUpdatedAt_evenWhenRequestIsAllNull() {
        Instant originalUpdatedAt = Instant.now().minusSeconds(3600);
        User user =
                new User(
                        1L,
                        "alovelace",
                        "hashed-secret",
                        UserRole.USER,
                        UserStatus.ACTIVE,
                        originalUpdatedAt,
                        originalUpdatedAt);

        UpdateUserRequest request = new UpdateUserRequest(null, null, null);

        mapper.updateEntity(user, request);

        assertThat(user.getUpdatedAt()).isAfter(originalUpdatedAt);
    }

    @Test
    void updateEntity_doesNotChangeId() {
        User user =
                new User(
                        42L,
                        "alovelace",
                        "hashed-secret",
                        UserRole.USER,
                        UserStatus.ACTIVE,
                        Instant.now(),
                        Instant.now());

        UpdateUserRequest request = new UpdateUserRequest(null, UserRole.ADMIN, null);

        mapper.updateEntity(user, request);

        assertThat(user.getId()).isEqualTo(42L);
    }

    @Test
    void toResponse_mapsFieldsAndExcludesPassword() {
        Instant createdAt = Instant.now().minusSeconds(100);
        Instant updatedAt = Instant.now();
        User user =
                new User(
                        7L,
                        "alovelace",
                        "hashed-secret",
                        UserRole.ADMIN,
                        UserStatus.ACTIVE,
                        createdAt,
                        updatedAt);

        UserResponse response = mapper.toResponse(user);

        assertThat(response.id()).isEqualTo(7L);
        assertThat(response.username()).isEqualTo("alovelace");
        assertThat(response.role()).isEqualTo(UserRole.ADMIN);
        assertThat(response.status()).isEqualTo(UserStatus.ACTIVE);
        assertThat(response.createdAt()).isEqualTo(createdAt);
        assertThat(response.updatedAt()).isEqualTo(updatedAt);
    }
}
