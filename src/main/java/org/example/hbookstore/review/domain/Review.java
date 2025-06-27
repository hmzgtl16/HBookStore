package org.example.hbookstore.review.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Data
@AllArgsConstructor
@Table("reviews")
public class Review {
    @Id
    private Long id;
    private Long userId;
    private Long bookId;
    private int rating;
    private String comment;
    private Instant createdAt;
    private Instant updatedAt;
}
