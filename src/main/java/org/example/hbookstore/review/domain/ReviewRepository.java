package org.example.hbookstore.review.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long>, PagingAndSortingRepository<Review, Long> {

    Page<Review> findByBookId(Long bookId, Pageable pageable);
    Page<Review> findByCustomerId(Long customerId, Pageable pageable);

    boolean existsByCustomerIdAndBookId(Long userId, Long bookId);
}
