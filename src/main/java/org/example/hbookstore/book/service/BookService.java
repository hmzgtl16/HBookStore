package org.example.hbookstore.book.service;

import org.example.hbookstore.book.api.dto.BookResponse;
import org.example.hbookstore.book.api.dto.CreateBookRequest;
import org.example.hbookstore.book.api.dto.UpdateBookRequest;
import org.example.hbookstore.book.domain.enums.BookCategory;
import org.example.hbookstore.book.domain.enums.BookFormat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookResponse createBook(CreateBookRequest request);

    BookResponse getBook(Long id);

    BookResponse updateBook(Long id, UpdateBookRequest request);

    void deleteBook(Long id) throws Exception;

    Page<BookResponse> getAllBooks(Pageable pageable);

    Page<BookResponse> getBooksByAuthor(Long authorId, Pageable pageable);

    Page<BookResponse> getBooksByCategory(BookCategory category, Pageable pageable);

    Page<BookResponse> getBooksByFormat(BookFormat format, Pageable pageable);

    Page<BookResponse> getBooksByLanguage(String language, Pageable pageable);

    Page<BookResponse> getBooksByPublisher(String publisher, Pageable pageable);

    Page<BookResponse> searchBooks(String query, Pageable pageable);
}
