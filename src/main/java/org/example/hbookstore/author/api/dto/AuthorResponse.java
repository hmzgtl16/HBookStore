package org.example.hbookstore.author.api.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AuthorResponse(
        Long id,
        String firstName,
        String lastName,
        String biography,
        LocalDate birthDate,
        String nationality,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
