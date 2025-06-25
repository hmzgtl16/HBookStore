package org.example.hbookstore.book.api.dto;

import org.example.hbookstore.book.domain.enums.BookCategory;
import org.example.hbookstore.book.domain.enums.BookFormat;

import java.time.LocalDate;

/**
 * DTO for {@link org.example.hbookstore.book.domain.Book}
 */
public record CreateBookRequest(
        String title,
        String description,
        Long authorId,
        String isbn,
        String publisher,
        LocalDate publishedDate,
        String language,
        BookFormat format,
        BookCategory category,
        String coverImageUrl
) {
}