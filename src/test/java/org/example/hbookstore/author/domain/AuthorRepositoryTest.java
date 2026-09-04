package org.example.hbookstore.author.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Instant;
import java.util.Optional;
import org.example.hbookstore.shared.AbstractDataJdbcTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@DisplayName("AuthorRepository Integration Tests")
class AuthorRepositoryTest extends AbstractDataJdbcTest {

    @Autowired private AuthorRepository authorRepository;

    @Test
    void save_persistsAuthor_andGeneratesId() {
        Author author =
                new Author(null, "Isaac", "Asimov", "American", Instant.now(), Instant.now());

        Author saved = authorRepository.save(author);

        Optional<Author> found = authorRepository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(saved.getId());
    }

    @Test
    void findById_returnsPersistedAuthor() {
        Author saved =
                authorRepository.save(
                        new Author(
                                null,
                                "Ursula",
                                "Le Guin",
                                "American",
                                Instant.now(),
                                Instant.now()));

        Optional<Author> found = authorRepository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getFirstName()).isEqualTo("Ursula");
        assertThat(found.get().getLastName()).isEqualTo("Le Guin");
    }

    @Test
    void findById_returnsEmpty_whenNotFound() {
        Optional<Author> found = authorRepository.findById(999_999L);

        assertThat(found).isEmpty();
    }

    @Test
    void update_persistsChanges() {
        Author saved =
                authorRepository.save(
                        new Author(
                                null,
                                "Frank",
                                "Herbert",
                                "American",
                                Instant.now(),
                                Instant.now()));

        saved.setNationality("American (Tacoma, WA)");
        authorRepository.save(saved);

        Author reloaded = authorRepository.findById(saved.getId()).orElseThrow();
        assertThat(reloaded.getNationality()).isEqualTo("American (Tacoma, WA)");
    }

    @Test
    void deleteById_removesAuthor() {
        Author saved =
                authorRepository.save(
                        new Author(
                                null,
                                "Philip K.",
                                "Dick",
                                "American",
                                Instant.now(),
                                Instant.now()));

        authorRepository.deleteById(saved.getId());

        assertThat(authorRepository.findById(saved.getId())).isEmpty();
    }

    @Test
    void findByNationality_returnsOnlyMatchingAuthors_paged() {
        authorRepository.save(
                new Author(null, "Isaac", "Asimov", "American", Instant.now(), Instant.now()));
        authorRepository.save(
                new Author(null, "Frank", "Herbert", "American", Instant.now(), Instant.now()));
        authorRepository.save(
                new Author(null, "Kazuo", "Ishiguro", "British", Instant.now(), Instant.now()));

        Page<Author> page = authorRepository.findByNationality("American", PageRequest.of(0, 10));

        assertThat(page.getTotalElements()).isEqualTo(3);
        assertThat(page.getContent())
                .extracting(Author::getLastName)
                .containsExactlyInAnyOrder("Lee", "Asimov", "Herbert");
    }

    @Test
    void findByNationality_respectsPageSize() {
        authorRepository.save(
                new Author(null, "Isaac", "Asimov", "American", Instant.now(), Instant.now()));
        authorRepository.save(
                new Author(null, "Frank", "Herbert", "American", Instant.now(), Instant.now()));

        Page<Author> firstPage =
                authorRepository.findByNationality("American", PageRequest.of(0, 2));

        assertThat(firstPage.getContent()).hasSize(2);
        assertThat(firstPage.getTotalPages()).isEqualTo(2);
        assertThat(firstPage.getTotalElements()).isEqualTo(3);
    }

    @Test
    void searchByFirstOrLastName_isCaseInsensitive_andMatchesEither() {
        authorRepository.save(
                new Author(null, "Isaac", "Asimov", "American", Instant.now(), Instant.now()));
        authorRepository.save(
                new Author(null, "Ursula", "Le Guin", "American", Instant.now(), Instant.now()));
        authorRepository.save(
                new Author(null, "Frank", "Herbert", "American", Instant.now(), Instant.now()));

        Page<Author> byFirstName =
                authorRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
                        "isa", "isa", PageRequest.of(0, 10));

        assertThat(byFirstName.getContent())
                .extracting(Author::getFirstName)
                .containsExactly("Isaac");

        Page<Author> byLastName =
                authorRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
                        "herb", "herb", PageRequest.of(0, 10));

        assertThat(byLastName.getContent())
                .extracting(Author::getLastName)
                .containsExactly("Herbert");
    }

    @Test
    void searchByFirstOrLastName_returnsEmptyPage_whenNoMatch() {
        authorRepository.save(
                new Author(null, "Isaac", "Asimov", "American", Instant.now(), Instant.now()));

        Page<Author> page =
                authorRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
                        "zzz", "zzz", PageRequest.of(0, 10));

        assertThat(page.getContent()).isEmpty();
        assertThat(page.getTotalElements()).isZero();
    }

    @Test
    void save_rejectsBlankFirstName_ifDbConstraintExists() {
        assertThatThrownBy(
                        () ->
                                authorRepository.save(
                                        new Author(
                                                null,
                                                null,
                                                "Asimov",
                                                "American",
                                                Instant.now(),
                                                Instant.now())))
                .isInstanceOf(RuntimeException.class);
    }
}
