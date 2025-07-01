package org.example.hbookstore.author.api.dto;

import java.time.Instant;

public record AuthorResponse(
        Long id,
        String firstName,
        String lastName,
        String nationality,
        Instant createdAt,
        Instant updatedAt
) {
}
