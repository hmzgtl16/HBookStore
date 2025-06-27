package org.example.hbookstore.review.mapping;

import org.example.hbookstore.review.api.dto.CreateReviewRequest;
import org.example.hbookstore.review.api.dto.ReviewResponse;
import org.example.hbookstore.review.api.dto.UpdateReviewRequest;
import org.example.hbookstore.review.domain.Review;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ReviewMapper {

    public Review toEntity(CreateReviewRequest request) {
        return new Review(
                null, // ID will be set by the repository
                request.bookId(),
                request.userId(),
                request.rating(),
                request.comment(),
                Instant.now(),
                Instant.now()
        );
    }

    public Review updateEntity(Review review, UpdateReviewRequest request) {
        if (request.rating() != 0) {
            review.setRating(request.rating());
        }
        if (request.comment() != null) {
            review.setComment(request.comment());
        }
        review.setUpdatedAt(Instant.now());
        return review;
    }

    public ReviewResponse toResponse(Review review) {
        return new ReviewResponse(
                review.getId(),
                review.getBookId(),
                review.getUserId(),
                review.getRating(),
                review.getComment(),
                review.getCreatedAt(),
                review.getUpdatedAt()
        );
    }
}
