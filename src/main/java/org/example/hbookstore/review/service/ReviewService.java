package org.example.hbookstore.review.service;

import org.example.hbookstore.review.api.dto.CreateReviewRequest;
import org.example.hbookstore.review.api.dto.ReviewResponse;
import org.example.hbookstore.review.api.dto.UpdateReviewRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {
    ReviewResponse createReview(CreateReviewRequest request);
    ReviewResponse getReview(Long id);
    ReviewResponse updateReview(Long id, UpdateReviewRequest request);
    void deleteReview(Long id);
    Page<ReviewResponse> getAllReviews(Pageable pageable);
    Page<ReviewResponse> getReviewsByBookId(Long bookId, Pageable pageable);
    Page<ReviewResponse> getReviewsByUserId(Long userId, Pageable pageable);
}
