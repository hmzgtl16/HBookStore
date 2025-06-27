package org.example.hbookstore.review.api.dto;

import java.time.Instant;

/**
 * DTO for {@link org.example.hbookstore.review.domain.Review}
 */
public record ReviewResponse(
        Long id,
        Long customerId,
        Long bookId,
        int rating,
        String comment,
        Instant createdAt,
        Instant updatedAt
) {
}