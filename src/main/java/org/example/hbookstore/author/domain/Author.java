package org.example.hbookstore.author.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Data
@AllArgsConstructor
@Table(name = "authors")
public class Author {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String nationality;

    private Instant createdAt;
    private Instant updatedAt;
}
