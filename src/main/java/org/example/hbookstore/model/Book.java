package org.example.hbookstore.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Data
@Table(name = "books")
public class Book {
    @Id
    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private Long authorId; // FK to Author
}