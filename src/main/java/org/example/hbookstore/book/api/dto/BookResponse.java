package org.example.hbookstore.book.api.dto;

import java.time.Instant;
import java.time.LocalDate;
import org.example.hbookstore.book.domain.enums.BookCategory;
import org.example.hbookstore.book.domain.enums.BookFormat;

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
        Instant createdAt,
        Instant updatedAt,
        Long authorId) {}
