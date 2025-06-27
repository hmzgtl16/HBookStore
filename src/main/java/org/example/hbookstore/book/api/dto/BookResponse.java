package org.example.hbookstore.book.api.dto;

import org.example.hbookstore.book.domain.enums.BookCategory;
import org.example.hbookstore.book.domain.enums.BookFormat;

import java.time.LocalDate;

public record BookResponse(
        Long id,
        String title,
        String description,
        String isbn,
        String publisher,
        LocalDate publishedDate,
        String language,
        BookFormat format,
        BookCategory category,
        String coverImageUrl,
        java.time.Instant createdAt,
        java.time.Instant updatedAt,
        Long authorId
) {
}
