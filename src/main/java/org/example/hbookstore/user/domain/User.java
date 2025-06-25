package org.example.hbookstore.user.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.hbookstore.user.domain.enums.UserRole;
import org.example.hbookstore.user.domain.enums.UserStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Table("users")
public class User {
        @Id
        private Long id;
        private String email;
        private String username;
        private String password;
        private UserRole role;
        private UserStatus status;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
}


