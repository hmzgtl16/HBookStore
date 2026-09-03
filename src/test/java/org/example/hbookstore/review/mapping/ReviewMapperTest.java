package org.example.hbookstore.review.mapping;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import org.example.hbookstore.review.api.dto.CreateReviewRequest;
import org.example.hbookstore.review.api.dto.ReviewResponse;
import org.example.hbookstore.review.api.dto.UpdateReviewRequest;
import org.example.hbookstore.review.domain.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ReviewMapper Unit Tests")
class ReviewMapperTest {

    private ReviewMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = ReviewMapper.INSTANCE;
    }

    @Test
    void toEntity_mapsFieldsAndGeneratesTimestamps_withNullId() {
        CreateReviewRequest request = new CreateReviewRequest(1L, 7L, 4, "Great read!");

        Review review = mapper.toEntity(request);

        assertThat(review.getId()).isNull();
        assertThat(review.getCustomerId()).isEqualTo(1L);
        assertThat(review.getBookId()).isEqualTo(7L);
        assertThat(review.getRating()).isEqualTo(4);
        assertThat(review.getComment()).isEqualTo("Great read!");
        assertThat(review.getCreatedAt()).isNotNull().isBeforeOrEqualTo(Instant.now());
        assertThat(review.getUpdatedAt()).isNotNull().isBeforeOrEqualTo(Instant.now());
    }

    @Test
    void updateEntity_updatesRating_whenNonZero() {
        Review review = new Review(1L, 1L, 7L, 3, "Decent", Instant.now(), Instant.now());
        UpdateReviewRequest request = new UpdateReviewRequest(5, null);

        mapper.updateEntity(review, request);

        assertThat(review.getRating()).isEqualTo(5); // updated
        assertThat(review.getComment()).isEqualTo("Decent"); // preserved
    }

    @Test
    void updateEntity_leavesRatingUnchanged_whenZero() {
        Review review = new Review(1L, 1L, 7L, 3, "Decent", Instant.now(), Instant.now());
        UpdateReviewRequest request = new UpdateReviewRequest(0, "Actually pretty good");

        mapper.updateEntity(review, request);

        assertThat(review.getRating()).isEqualTo(3); // untouched, 0 treated as "not provided"
        assertThat(review.getComment()).isEqualTo("Actually pretty good"); // updated
    }

    @Test
    void updateEntity_appliesOnlyNonNullComment() {
        Review review = new Review(1L, 1L, 7L, 3, "Decent", Instant.now(), Instant.now());
        UpdateReviewRequest request = new UpdateReviewRequest(0, null);

        mapper.updateEntity(review, request);

        assertThat(review.getComment()).isEqualTo("Decent"); // preserved
    }

    @Test
    void updateEntity_alwaysTouchesUpdatedAt_evenWhenRequestIsEmpty() {
        Instant originalUpdatedAt = Instant.now().minusSeconds(3600);
        Review review = new Review(1L, 1L, 7L, 3, "Decent", originalUpdatedAt, originalUpdatedAt);
        UpdateReviewRequest request = new UpdateReviewRequest(0, null);

        mapper.updateEntity(review, request);

        assertThat(review.getUpdatedAt()).isAfter(originalUpdatedAt);
    }

    @Test
    void updateEntity_doesNotChangeIdOrCreatedAt() {
        Instant originalCreatedAt = Instant.now().minusSeconds(3600);
        Review review = new Review(42L, 1L, 7L, 3, "Decent", originalCreatedAt, originalCreatedAt);
        UpdateReviewRequest request = new UpdateReviewRequest(5, "Updated comment");

        mapper.updateEntity(review, request);

        assertThat(review.getId()).isEqualTo(42L);
        assertThat(review.getCreatedAt()).isEqualTo(originalCreatedAt);
    }

    @Test
    void toResponse_mapsAllFields() {
        Instant createdAt = Instant.now().minusSeconds(100);
        Instant updatedAt = Instant.now();
        Review review = new Review(9L, 1L, 7L, 4, "Great read!", createdAt, updatedAt);

        ReviewResponse response = mapper.toResponse(review);

        assertThat(response.id()).isEqualTo(9L);
        assertThat(response.customerId()).isEqualTo(1L);
        assertThat(response.bookId()).isEqualTo(7L);
        assertThat(response.rating()).isEqualTo(4);
        assertThat(response.comment()).isEqualTo("Great read!");
        assertThat(response.createdAt()).isEqualTo(createdAt);
        assertThat(response.updatedAt()).isEqualTo(updatedAt);
    }
}
