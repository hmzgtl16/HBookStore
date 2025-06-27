package org.example.hbookstore.review.api.dto;

/**
 * DTO for {@link org.example.hbookstore.review.domain.Review}
 */
public record CreateReviewRequest(
        Long customerId,
        Long bookId,
        int rating,
        String comment
) {
}