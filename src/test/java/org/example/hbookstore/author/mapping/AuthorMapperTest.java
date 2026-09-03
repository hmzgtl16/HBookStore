package org.example.hbookstore.author.mapping;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import org.example.hbookstore.author.api.dto.AuthorResponse;
import org.example.hbookstore.author.api.dto.CreateAuthorRequest;
import org.example.hbookstore.author.api.dto.UpdateAuthorRequest;
import org.example.hbookstore.author.domain.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("AuthorMapper Unit Tests")
class AuthorMapperTest {

    private AuthorMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = AuthorMapper.INSTANCE;
    }

    @Test
    void toEntity_mapsFieldsAndGeneratesTimestamps_withNullId() {
        CreateAuthorRequest request = new CreateAuthorRequest("Isaac", "Asimov", "American");

        Author author = mapper.toEntity(request);

        assertThat(author.getId()).isNull();
        assertThat(author.getFirstName()).isEqualTo("Isaac");
        assertThat(author.getLastName()).isEqualTo("Asimov");
        assertThat(author.getNationality()).isEqualTo("American");
        assertThat(author.getCreatedAt()).isNotNull().isBeforeOrEqualTo(Instant.now());
        assertThat(author.getUpdatedAt()).isNotNull().isBeforeOrEqualTo(Instant.now());
    }

    @Test
    void updateEntity_appliesOnlyNonNullFields_andPreservesOthers() {
        Instant originalCreatedAt = Instant.now().minusSeconds(3600);
        Author author =
                new Author(1L, "Isaac", "Asimov", "American", originalCreatedAt, originalCreatedAt);

        UpdateAuthorRequest request = new UpdateAuthorRequest("Isaak", null, null);

        mapper.updateEntity(author, request);

        assertThat(author.getId()).isEqualTo(1L);
        assertThat(author.getFirstName()).isEqualTo("Isaak"); // updated
        assertThat(author.getLastName()).isEqualTo("Asimov"); // preserved (was null in request)
        assertThat(author.getNationality()).isEqualTo("American"); // preserved
        assertThat(author.getCreatedAt()).isEqualTo(originalCreatedAt); // untouched
    }

    @Test
    void updateEntity_alwaysTouchesUpdatedAt_evenWhenRequestIsAllNull() {
        Instant originalUpdatedAt = Instant.now().minusSeconds(3600);
        Author author =
                new Author(1L, "Isaac", "Asimov", "American", originalUpdatedAt, originalUpdatedAt);

        UpdateAuthorRequest request = new UpdateAuthorRequest(null, null, null);

        mapper.updateEntity(author, request);

        assertThat(author.getUpdatedAt()).isAfter(originalUpdatedAt);
    }

    @Test
    void updateEntity_doesNotChangeId() {
        Author author =
                new Author(42L, "Isaac", "Asimov", "American", Instant.now(), Instant.now());
        UpdateAuthorRequest request = new UpdateAuthorRequest("Isaak", "Asimoff", "Russian");

        mapper.updateEntity(author, request);

        assertThat(author.getId()).isEqualTo(42L);
    }

    @Test
    void toResponse_mapsAllFields() {
        Instant createdAt = Instant.now().minusSeconds(100);
        Instant updatedAt = Instant.now();
        Author author = new Author(7L, "Isaac", "Asimov", "American", createdAt, updatedAt);

        AuthorResponse response = mapper.toResponse(author);

        assertThat(response.id()).isEqualTo(7L);
        assertThat(response.firstName()).isEqualTo("Isaac");
        assertThat(response.lastName()).isEqualTo("Asimov");
        assertThat(response.nationality()).isEqualTo("American");
        assertThat(response.createdAt()).isEqualTo(createdAt);
        assertThat(response.updatedAt()).isEqualTo(updatedAt);
    }
}
