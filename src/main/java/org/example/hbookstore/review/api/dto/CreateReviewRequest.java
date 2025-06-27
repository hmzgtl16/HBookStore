package org.example.hbookstore.review.api.dto;

/**
 * DTO for {@link org.example.hbookstore.review.domain.Review}
 */
public record CreateReviewRequest(
        Long userId,
        Long bookId,
        int rating,
        String comment
) {
}