package org.example.hbookstore.author.api.dto;

import java.time.LocalDate;

public record AuthorResponse(
        Long id,
        String firstName,
        String lastName,
        String nationality,
        java.time.Instant createdAt,
        java.time.Instant updatedAt
) {
}
