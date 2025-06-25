package org.example.hbookstore.author.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Table(name = "authors")
public class Author {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String biography;
    private LocalDate birthDate;
    private String nationality;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
