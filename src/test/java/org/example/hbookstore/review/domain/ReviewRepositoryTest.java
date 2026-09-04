package org.example.hbookstore.review.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import org.example.hbookstore.shared.AbstractDataJdbcTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@DisplayName("ReviewRepository Integration Tests")
class ReviewRepositoryTest extends AbstractDataJdbcTest {

    @Autowired private ReviewRepository reviewRepository;

    @Test
    void findByBookId_returnsMatchingReviews() {
        Review saved =
                reviewRepository.save(
                        new Review(
                                null, 2L, 2L, 4, "Solid follow-up.", Instant.now(), Instant.now()));

        Page<Review> page = reviewRepository.findByBookId(2L, PageRequest.of(0, 10));

        assertThat(page.getContent()).extracting(Review::getId).contains(saved.getId());
        assertThat(page.getContent())
                .allSatisfy(review -> assertThat(review.getBookId()).isEqualTo(2L));
    }

    @Test
    void findByBookId_returnsEmptyPage_whenNoReviews() {
        Page<Review> page = reviewRepository.findByBookId(999_999L, PageRequest.of(0, 10));

        assertThat(page.getContent()).isEmpty();
    }

    @Test
    void findByCustomerId_returnsMatchingReviews() {
        Review saved =
                reviewRepository.save(
                        new Review(null, 2L, 2L, 3, "Decent read.", Instant.now(), Instant.now()));

        Page<Review> page = reviewRepository.findByCustomerId(2L, PageRequest.of(0, 10));

        assertThat(page.getContent()).extracting(Review::getId).contains(saved.getId());
        assertThat(page.getContent())
                .allSatisfy(review -> assertThat(review.getCustomerId()).isEqualTo(2L));
    }

    @Test
    void findByCustomerId_returnsEmptyPage_whenNoReviews() {
        Page<Review> page = reviewRepository.findByCustomerId(999_999L, PageRequest.of(0, 10));

        assertThat(page.getContent()).isEmpty();
    }

    @Test
    void existsByCustomerIdAndBookId_returnsTrue_whenReviewExists() {
        reviewRepository.save(
                new Review(null, 1L, 3L, 5, "Loved it.", Instant.now(), Instant.now()));

        assertThat(reviewRepository.existsByCustomerIdAndBookId(1L, 3L)).isTrue();
    }

    @Test
    void existsByCustomerIdAndBookId_returnsFalse_whenReviewAbsent() {
        assertThat(reviewRepository.existsByCustomerIdAndBookId(999_999L, 999_999L)).isFalse();
    }
}
