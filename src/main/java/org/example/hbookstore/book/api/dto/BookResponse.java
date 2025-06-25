package org.example.hbookstore.book.api.dto;

import org.example.hbookstore.book.domain.enums.BookCategory;
import org.example.hbookstore.book.domain.enums.BookFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long authorId
) {
}
