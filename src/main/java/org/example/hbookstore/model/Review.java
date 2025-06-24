package org.example.hbookstore.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("reviews")
public class Review {
    @Id
    private Long id;
    private Long userId;
    private Long bookId;
    private int rating;
    private String comment;
}
