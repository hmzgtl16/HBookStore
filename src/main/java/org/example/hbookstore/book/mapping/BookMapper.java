package org.example.hbookstore.book.mapping;

import org.example.hbookstore.book.api.dto.BookResponse;
import org.example.hbookstore.book.api.dto.CreateBookRequest;
import org.example.hbookstore.book.api.dto.UpdateBookRequest;
import org.example.hbookstore.book.domain.Book;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class BookMapper {

    public Book toEntity(CreateBookRequest request) {
        return new Book(
                null, // ID will be generated by the database
                request.title(),
                request.description(),
                request.authorId(),
                request.isbn(),
                request.publisher(),
                request.publishedDate(),
                request.language(),
                request.format(),
                request.category(),
                request.coverImageUrl(),
                Instant.now(),
                Instant.now()
        );
    }

    public Book updateEntity(Book book, UpdateBookRequest request) {
        if (request.title() != null) {
            book.setTitle(request.title());
        }
        if (request.description() != null) {
            book.setDescription(request.description());
        }
        if (request.authorId() != null) {
            book.setAuthorId(request.authorId());
        }
        if (request.isbn() != null) {
            book.setIsbn(request.isbn());
        }
        if (request.publisher() != null) {
            book.setPublisher(request.publisher());
        }
        if (request.publishedDate() != null) {
            book.setPublishedDate(request.publishedDate());
        }
        if (request.language() != null) {
            book.setLanguage(request.language());
        }
        if (request.format() != null) {
            book.setFormat(request.format());
        }
        if (request.category() != null) {
            book.setCategory(request.category());
        }
        if (request.coverImageUrl() != null) {
            book.setCoverImageUrl(request.coverImageUrl());
        }
        book.setUpdatedAt(Instant.now());
        return book;
    }

    public BookResponse toResponse(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getDescription(),
                book.getIsbn(),
                book.getPublisher(),
                book.getPublishedDate(),
                book.getLanguage(),
                book.getFormat(),
                book.getCategory(),
                book.getCoverImageUrl(),
                book.getCreatedAt(),
                book.getUpdatedAt(),
                book.getAuthorId()
        );
    }
}
