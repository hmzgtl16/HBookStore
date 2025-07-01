package org.example.hbookstore.book.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.hbookstore.book.api.dto.BookResponse;
import org.example.hbookstore.book.api.dto.CreateBookRequest;
import org.example.hbookstore.book.api.dto.UpdateBookRequest;
import org.example.hbookstore.book.domain.enums.BookCategory;
import org.example.hbookstore.book.domain.enums.BookFormat;
import org.example.hbookstore.book.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Book Management",
        description = "APIs for managing books in the bookstore system."
)
@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<BookResponse> createBook(CreateBookRequest request) {
        BookResponse response = bookService.createBook(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBook(@PathVariable Long id) {
        BookResponse response = bookService.getBook(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(
            @PathVariable Long id,
            @RequestBody UpdateBookRequest request
    ) {
        BookResponse response = bookService.updateBook(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) throws Exception {
        bookService.deleteBook(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<Page<BookResponse>> getAllBooks(
            @PageableDefault(size = 20) Pageable pageable
    ) {
        Page<BookResponse> response = bookService.getAllBooks(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<Page<BookResponse>> getBooksByAuthor(
            @PathVariable Long authorId,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        Page<BookResponse> response = bookService.getBooksByAuthor(authorId, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<Page<BookResponse>> getBooksByCategory(
            @PathVariable BookCategory category,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        Page<BookResponse> response = bookService.getBooksByCategory(category, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/format/{format}")
    public ResponseEntity<Page<BookResponse>> getBooksByFormat(
            @PathVariable BookFormat format,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        Page<BookResponse> response = bookService.getBooksByFormat(format, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/language/{language}")
    public ResponseEntity<Page<BookResponse>> getBooksByLanguage(
            @PathVariable String language,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        Page<BookResponse> response = bookService.getBooksByLanguage(language, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/publisher/{publisher}")
    public ResponseEntity<Page<BookResponse>> getBooksByPublisher(
            @PathVariable String publisher,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        Page<BookResponse> response = bookService.getBooksByPublisher(publisher, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<BookResponse>> searchBooks(
            @RequestParam String query,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        Page<BookResponse> response = bookService.searchBooks(query, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
