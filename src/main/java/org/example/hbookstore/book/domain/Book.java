package org.example.hbookstore.book.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.hbookstore.book.domain.enums.BookCategory;
import org.example.hbookstore.book.domain.enums.BookFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Table(name = "books")
public class Book {
    @Id
    private Long id;
    private String title;
    private String description;
    private Long authorId; // FK to Author

    private String isbn;
    private String publisher;
    private LocalDate publishedDate;
    private String language;
    private BookFormat format;
    private BookCategory category;
    private String coverImageUrl;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}