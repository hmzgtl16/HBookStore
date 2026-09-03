package org.example.hbookstore.book.mapping;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.LocalDate;
import org.example.hbookstore.book.api.dto.BookResponse;
import org.example.hbookstore.book.api.dto.CreateBookRequest;
import org.example.hbookstore.book.api.dto.UpdateBookRequest;
import org.example.hbookstore.book.domain.Book;
import org.example.hbookstore.book.domain.enums.BookCategory;
import org.example.hbookstore.book.domain.enums.BookFormat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("BookMapper Unit Tests")
class BookMapperTest {

    private BookMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = BookMapper.INSTANCE;
    }

    @Test
    void toEntity_mapsFieldsAndGeneratesTimestamps_withNullId() {
        CreateBookRequest request =
                new CreateBookRequest(
                        "Foundation",
                        "Sci-fi classic",
                        1L,
                        "978-0553293357",
                        "Bantam Books",
                        LocalDate.of(1951, 5, 1),
                        "English",
                        BookFormat.PAPERBACK,
                        BookCategory.SCIENCE,
                        "https://covers.example.com/foundation.jpg");

        Book book = mapper.toEntity(request);

        assertThat(book.getId()).isNull();
        assertThat(book.getTitle()).isEqualTo("Foundation");
        assertThat(book.getDescription()).isEqualTo("Sci-fi classic");
        assertThat(book.getAuthorId()).isEqualTo(1L);
        assertThat(book.getIsbn()).isEqualTo("978-0553293357");
        assertThat(book.getPublisher()).isEqualTo("Bantam Books");
        assertThat(book.getPublishedDate()).isEqualTo(LocalDate.of(1951, 5, 1));
        assertThat(book.getLanguage()).isEqualTo("English");
        assertThat(book.getFormat()).isEqualTo(BookFormat.PAPERBACK);
        assertThat(book.getCategory()).isEqualTo(BookCategory.SCIENCE);
        assertThat(book.getCoverImageUrl()).isEqualTo("https://covers.example.com/foundation.jpg");
        assertThat(book.getCreatedAt()).isNotNull().isBeforeOrEqualTo(Instant.now());
        assertThat(book.getUpdatedAt()).isNotNull().isBeforeOrEqualTo(Instant.now());
    }

    @Test
    void updateEntity_appliesOnlyNonNullFields_andPreservesOthers() {
        Instant originalCreatedAt = Instant.now().minusSeconds(3600);
        Book book =
                new Book(
                        1L,
                        "Foundation",
                        "Sci-fi classic",
                        1L,
                        "978-0553293357",
                        "Bantam Books",
                        LocalDate.of(1951, 5, 1),
                        "English",
                        BookFormat.PAPERBACK,
                        BookCategory.SCIENCE,
                        "https://covers.example.com/foundation.jpg",
                        originalCreatedAt,
                        originalCreatedAt);

        UpdateBookRequest request =
                new UpdateBookRequest(
                        "Foundation (Revised)",
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);

        mapper.updateEntity(book, request);

        assertThat(book.getTitle()).isEqualTo("Foundation (Revised)"); // updated
        assertThat(book.getDescription()).isEqualTo("Sci-fi classic"); // preserved
        assertThat(book.getAuthorId()).isEqualTo(1L); // preserved
        assertThat(book.getIsbn()).isEqualTo("978-0553293357"); // preserved
        assertThat(book.getPublisher()).isEqualTo("Bantam Books"); // preserved
        assertThat(book.getPublishedDate()).isEqualTo(LocalDate.of(1951, 5, 1)); // preserved
        assertThat(book.getLanguage()).isEqualTo("English"); // preserved
        assertThat(book.getFormat()).isEqualTo(BookFormat.PAPERBACK); // preserved
        assertThat(book.getCategory()).isEqualTo(BookCategory.SCIENCE); // preserved
        assertThat(book.getCoverImageUrl())
                .isEqualTo("https://covers.example.com/foundation.jpg"); // preserved
        assertThat(book.getCreatedAt()).isEqualTo(originalCreatedAt); // untouched
    }

    @Test
    void updateEntity_alwaysTouchesUpdatedAt_evenWhenRequestIsAllNull() {
        Instant originalUpdatedAt = Instant.now().minusSeconds(3600);
        Book book =
                new Book(
                        1L,
                        "Foundation",
                        "Sci-fi classic",
                        1L,
                        "978-0553293357",
                        "Bantam Books",
                        LocalDate.of(1951, 5, 1),
                        "English",
                        BookFormat.PAPERBACK,
                        BookCategory.SCIENCE,
                        "https://covers.example.com/foundation.jpg",
                        originalUpdatedAt,
                        originalUpdatedAt);

        UpdateBookRequest request =
                new UpdateBookRequest(null, null, null, null, null, null, null, null, null, null);

        mapper.updateEntity(book, request);

        assertThat(book.getUpdatedAt()).isAfter(originalUpdatedAt);
    }

    @Test
    void updateEntity_doesNotChangeId() {
        Book book =
                new Book(
                        42L,
                        "Foundation",
                        "Sci-fi classic",
                        1L,
                        "978-0553293357",
                        "Bantam Books",
                        LocalDate.of(1951, 5, 1),
                        "English",
                        BookFormat.PAPERBACK,
                        BookCategory.SCIENCE,
                        "https://covers.example.com/foundation.jpg",
                        Instant.now(),
                        Instant.now());

        UpdateBookRequest request =
                new UpdateBookRequest(
                        "New Title", null, null, null, null, null, null, null, null, null);

        mapper.updateEntity(book, request);

        assertThat(book.getId()).isEqualTo(42L);
    }

    @Test
    void toResponse_mapsAllFields() {
        Instant createdAt = Instant.now().minusSeconds(100);
        Instant updatedAt = Instant.now();
        Book book =
                new Book(
                        7L,
                        "Foundation",
                        "Sci-fi classic",
                        1L,
                        "978-0553293357",
                        "Bantam Books",
                        LocalDate.of(1951, 5, 1),
                        "English",
                        BookFormat.PAPERBACK,
                        BookCategory.SCIENCE,
                        "https://covers.example.com/foundation.jpg",
                        createdAt,
                        updatedAt);

        BookResponse response = mapper.toResponse(book);

        assertThat(response.id()).isEqualTo(7L);
        assertThat(response.title()).isEqualTo("Foundation");
        assertThat(response.description()).isEqualTo("Sci-fi classic");
        assertThat(response.isbn()).isEqualTo("978-0553293357");
        assertThat(response.publisher()).isEqualTo("Bantam Books");
        assertThat(response.publishedDate()).isEqualTo(LocalDate.of(1951, 5, 1));
        assertThat(response.language()).isEqualTo("English");
        assertThat(response.format()).isEqualTo(BookFormat.PAPERBACK);
        assertThat(response.category()).isEqualTo(BookCategory.SCIENCE);
        assertThat(response.coverImageUrl()).isEqualTo("https://covers.example.com/foundation.jpg");
        assertThat(response.createdAt()).isEqualTo(createdAt);
        assertThat(response.updatedAt()).isEqualTo(updatedAt);
        assertThat(response.authorId()).isEqualTo(1L);
    }
}
