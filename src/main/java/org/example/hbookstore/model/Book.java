package org.example.hbookstore.model;

import lombok.Data;
import org.example.hbookstore.model.enums.BookCategory;
import org.example.hbookstore.model.enums.BookFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Table(name = "books")
public class Book {
    @Id
    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private Long authorId; // FK to Author

    private String isbn;
    private String publisher;
    private LocalDate publicationDate;
    private String language;
    private BookFormat format;
    private BookCategory category;
    private String coverImageUrl;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}