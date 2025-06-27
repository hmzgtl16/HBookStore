package org.example.hbookstore.review.service;

import org.example.hbookstore.book.domain.BookRepository;
import org.example.hbookstore.review.api.dto.CreateReviewRequest;
import org.example.hbookstore.review.api.dto.ReviewResponse;
import org.example.hbookstore.review.api.dto.UpdateReviewRequest;
import org.example.hbookstore.review.domain.Review;
import org.example.hbookstore.review.domain.ReviewRepository;
import org.example.hbookstore.review.mapping.ReviewMapper;
import org.example.hbookstore.shared.error.EntityNotFoundException;
import org.example.hbookstore.shared.error.InvalidRequestException;
import org.example.hbookstore.user.domain.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ReviewMapper reviewMapper;

    public ReviewServiceImpl(
            ReviewRepository reviewRepository,
            BookRepository bookRepository,
            UserRepository userRepository,
            ReviewMapper reviewMapper
    ) {
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.reviewMapper = reviewMapper;
    }

    @Transactional
    @Override
    public ReviewResponse createReview(CreateReviewRequest request) {
        validateReview(request);
        Review review = reviewMapper.toEntity(request);
        return reviewMapper.toResponse(reviewRepository.save(review));
    }

    @Transactional(readOnly = true)
    @Override
    public ReviewResponse getReview(Long id) {
        return reviewRepository.findById(id)
                .map(reviewMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Review not found with id: " + id));
    }

    @Transactional
    @Override
    public ReviewResponse updateReview(Long id, UpdateReviewRequest request) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review not found with id: " + id));
        
        Review updatedReview = reviewMapper.updateEntity(review, request);
        return reviewMapper.toResponse(reviewRepository.save(updatedReview));
    }

    @Transactional
    @Override
    public void deleteReview(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new EntityNotFoundException("Review not found with id: " + id);
        }
        reviewRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ReviewResponse> getAllReviews(Pageable pageable) {
        return reviewRepository.findAll(pageable)
                .map(reviewMapper::toResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ReviewResponse> getReviewsByBookId(Long bookId, Pageable pageable) {
        return reviewRepository.findByBookId(bookId, pageable)
                .map(reviewMapper::toResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ReviewResponse> getReviewsByUserId(Long userId, Pageable pageable) {
        return reviewRepository.findByUserId(userId, pageable)
                .map(reviewMapper::toResponse);
    }

    private void validateReview(CreateReviewRequest request) {
        if (reviewRepository.existsByUserIdAndBookId(request.userId(),  request.bookId())) {
            throw new InvalidRequestException("Review already exists: " + request.userId() + ", " + request.bookId());
        }
        if (!bookRepository.existsById(request.bookId())) {
            throw new EntityNotFoundException("Book not found with id: " + request.bookId());
        }
        if (!userRepository.existsById(request.userId())) {
            throw new EntityNotFoundException("User not found with id: " + request.userId());
        }
    }
}
