package org.example.hbookstore.book.service;

import org.example.hbookstore.book.api.dto.BookResponse;
import org.example.hbookstore.book.api.dto.CreateBookRequest;
import org.example.hbookstore.book.api.dto.UpdateBookRequest;
import org.example.hbookstore.book.domain.Book;
import org.example.hbookstore.book.domain.BookRepository;
import org.example.hbookstore.book.domain.enums.BookCategory;
import org.example.hbookstore.book.domain.enums.BookFormat;
import org.example.hbookstore.book.mapping.BookMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookServiceImpl(
            BookRepository bookRepository,
            BookMapper bookMapper
    ) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Transactional
    @Override
    public BookResponse createBook(CreateBookRequest request) {
        Book book = bookMapper.toEntity(request);
        return bookMapper.toResponse(bookRepository.save(book));
    }

    @Transactional(readOnly = true)
    @Override
    public BookResponse getBook(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toResponse)
                .orElseThrow();  // Consider throwing an exception instead of returning null
    }

    @Transactional
    @Override
    public BookResponse updateBook(Long id, UpdateBookRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow();  // Handle not found case

        Book updatedBook = bookMapper.updateEntity(book, request);
        return bookMapper.toResponse(bookRepository.save(updatedBook));
    }

    @Transactional
    @Override
    public void deleteBook(Long id) throws Exception {
        if (!bookRepository.existsById(id)) {
            throw new Exception("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<BookResponse> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(bookMapper::toResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<BookResponse> getBooksByAuthor(Long authorId, Pageable pageable) {
        return bookRepository.findByAuthorId(authorId, pageable)
                .map(bookMapper::toResponse);
    }

    @Override
    public Page<BookResponse> getBooksByCategory(BookCategory category, Pageable pageable) {
        return bookRepository.findByCategory(category, pageable)
                .map(bookMapper::toResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<BookResponse> getBooksByFormat(BookFormat format, Pageable pageable) {
        return bookRepository.findByFormat(format, pageable)
                .map(bookMapper::toResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<BookResponse> getBooksByLanguage(String language, Pageable pageable) {
        return bookRepository.findByLanguage(language, pageable)
                .map(bookMapper::toResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<BookResponse> getBooksByPublisher(String publisher, Pageable pageable) {
        return bookRepository.findByPublisher(publisher, pageable)
                .map(bookMapper::toResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<BookResponse> searchBooks(String query, Pageable pageable) {
        return bookRepository
                .findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrPublisherContainingIgnoreCase(
                        query,
                        query,
                        query,
                        pageable
                )
                .map(bookMapper::toResponse);
    }
}
