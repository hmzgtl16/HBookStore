package org.example.hbookstore.repository;

import org.example.hbookstore.model.Review;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, Long> {}
