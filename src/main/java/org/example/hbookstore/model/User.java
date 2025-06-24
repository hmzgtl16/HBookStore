package org.example.hbookstore.model;

import lombok.Data;
import org.example.hbookstore.model.enums.UserRole;
import org.example.hbookstore.model.enums.UserStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("users")
public class User {
    @Id
    private Long id;
    private String username;
    private String password;
    private UserRole role;
    private UserStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}


