package org.example.hbookstore.review.api.dto;

import org.example.hbookstore.review.domain.Review;

/**
 * DTO for {@link Review}
 */
public record UpdateReviewRequest(
        int rating,
        String comment
) {
}