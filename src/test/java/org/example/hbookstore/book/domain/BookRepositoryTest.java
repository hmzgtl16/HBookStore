package org.example.hbookstore.book.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.LocalDate;
import org.example.hbookstore.book.domain.enums.BookCategory;
import org.example.hbookstore.book.domain.enums.BookFormat;
import org.example.hbookstore.shared.AbstractDataJdbcTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@DisplayName("BookRepository Integration Tests")
class BookRepositoryTest extends AbstractDataJdbcTest {

    @Autowired private BookRepository bookRepository;

    private Book newBook(
            String title,
            Long authorId,
            String isbn,
            String publisher,
            String language,
            BookFormat format,
            BookCategory category) {
        return new Book(
                null,
                title,
                "Description of " + title,
                authorId,
                isbn,
                publisher,
                LocalDate.of(2000, 1, 1),
                language,
                format,
                category,
                null,
                Instant.now(),
                Instant.now());
    }

    @Test
    void existsByIsbn_returnsTrue_whenIsbnPresent() {
        bookRepository.save(
                newBook(
                        "Dune",
                        1L,
                        "9780441013593",
                        "Ace Books",
                        "English",
                        BookFormat.PAPERBACK,
                        BookCategory.SCIENCE));

        assertThat(bookRepository.existsByIsbn("9780441013593")).isTrue();
    }

    @Test
    void existsByIsbn_returnsFalse_whenIsbnAbsent() {
        assertThat(bookRepository.existsByIsbn("0000000000000")).isFalse();
    }

    @Test
    void findByAuthorId_returnsOnlyMatchingBooks() {
        bookRepository.save(
                newBook(
                        "Dune",
                        1L,
                        "9780441013593",
                        "Ace Books",
                        "English",
                        BookFormat.PAPERBACK,
                        BookCategory.SCIENCE));
        bookRepository.save(
                newBook(
                        "Foundation",
                        1L,
                        "9780553293357",
                        "Bantam",
                        "English",
                        BookFormat.PAPERBACK,
                        BookCategory.SCIENCE));
        bookRepository.save(
                newBook(
                        "Neuromancer",
                        2L,
                        "9780441569595",
                        "Ace Books",
                        "English",
                        BookFormat.PAPERBACK,
                        BookCategory.SCIENCE));

        Page<Book> page = bookRepository.findByAuthorId(1L, PageRequest.of(0, 10));

        assertThat(page.getContent())
                .extracting(Book::getTitle)
                .containsExactlyInAnyOrder("1984", "Dune", "Foundation");
    }

    @Test
    void findByCategory_returnsOnlyMatchingBooks() {
        bookRepository.save(
                newBook(
                        "Dune",
                        1L,
                        "9780441013593",
                        "Ace Books",
                        "English",
                        BookFormat.PAPERBACK,
                        BookCategory.SCIENCE));
        bookRepository.save(
                newBook(
                        "The Hobbit",
                        2L,
                        "9780345339683",
                        "Ballantine",
                        "English",
                        BookFormat.PAPERBACK,
                        BookCategory.FANTASY));

        Page<Book> page =
                bookRepository.findByCategory(BookCategory.SCIENCE, PageRequest.of(0, 10));

        assertThat(page.getContent()).extracting(Book::getTitle).containsExactly("Dune");
    }

    @Test
    void findByFormat_returnsOnlyMatchingBooks() {
        bookRepository.save(
                newBook(
                        "Dune",
                        1L,
                        "9780441013593",
                        "Ace Books",
                        "English",
                        BookFormat.PAPERBACK,
                        BookCategory.SCIENCE));
        bookRepository.save(
                newBook(
                        "The Hobbit",
                        2L,
                        "9780345339683",
                        "Ballantine",
                        "English",
                        BookFormat.HARDCOVER,
                        BookCategory.FANTASY));

        Page<Book> page = bookRepository.findByFormat(BookFormat.HARDCOVER, PageRequest.of(0, 10));

        assertThat(page.getContent())
                .extracting(Book::getTitle)
                .containsExactly("Harry Potter and the Philosopher's Stone", "The Hobbit");
    }

    @Test
    void findByLanguage_returnsOnlyMatchingBooks() {
        bookRepository.save(
                newBook(
                        "Dune",
                        1L,
                        "9780441013593",
                        "Ace Books",
                        "French",
                        BookFormat.PAPERBACK,
                        BookCategory.SCIENCE));
        bookRepository.save(
                newBook(
                        "The Hobbit",
                        2L,
                        "9780345339683",
                        "Ballantine",
                        "English",
                        BookFormat.HARDCOVER,
                        BookCategory.FANTASY));

        Page<Book> page = bookRepository.findByLanguage("French", PageRequest.of(0, 10));

        assertThat(page.getContent()).extracting(Book::getTitle).containsExactly("Dune");
    }

    @Test
    void findByPublisher_returnsOnlyMatchingBooks() {
        bookRepository.save(
                newBook(
                        "Dune",
                        1L,
                        "9780441013593",
                        "Ace Books",
                        "English",
                        BookFormat.PAPERBACK,
                        BookCategory.SCIENCE));
        bookRepository.save(
                newBook(
                        "The Hobbit",
                        2L,
                        "9780345339683",
                        "Ballantine",
                        "English",
                        BookFormat.HARDCOVER,
                        BookCategory.FANTASY));

        Page<Book> page = bookRepository.findByPublisher("Ace Books", PageRequest.of(0, 10));

        assertThat(page.getContent()).extracting(Book::getTitle).containsExactly("Dune");
    }

    @Test
    void search_matchesTitleDescriptionOrPublisher_caseInsensitively() {
        bookRepository.save(
                newBook(
                        "Dune",
                        1L,
                        "9780441013593",
                        "Ace Books",
                        "English",
                        BookFormat.PAPERBACK,
                        BookCategory.SCIENCE));
        bookRepository.save(
                newBook(
                        "The Hobbit",
                        2L,
                        "9780345339683",
                        "Ballantine",
                        "English",
                        BookFormat.HARDCOVER,
                        BookCategory.FANTASY));

        Page<Book> byTitle =
                bookRepository
                        .findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrPublisherContainingIgnoreCase(
                                "dun", "dun", "dun", PageRequest.of(0, 10));
        assertThat(byTitle.getContent()).extracting(Book::getTitle).containsExactly("Dune");

        Page<Book> byPublisher =
                bookRepository
                        .findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrPublisherContainingIgnoreCase(
                                "ballantine", "ballantine", "ballantine", PageRequest.of(0, 10));
        assertThat(byPublisher.getContent())
                .extracting(Book::getTitle)
                .containsExactly("The Hobbit");
    }

    @Test
    void search_returnsEmptyPage_whenNoMatch() {
        bookRepository.save(
                newBook(
                        "Dune",
                        1L,
                        "9780441013593",
                        "Ace Books",
                        "English",
                        BookFormat.PAPERBACK,
                        BookCategory.SCIENCE));

        Page<Book> page =
                bookRepository
                        .findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrPublisherContainingIgnoreCase(
                                "zzz", "zzz", "zzz", PageRequest.of(0, 10));

        assertThat(page.getContent()).isEmpty();
    }
}
