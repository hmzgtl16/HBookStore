package org.example.hbookstore.author.api.dto;

import jakarta.validation.constraints.Size;

/**
 * DTO for {@link org.example.hbookstore.author.domain.Author}
 */
public record UpdateAuthorRequest(
        @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
        String firstName,

        @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
        String lastName,

        @Size(min = 2, max = 100, message = "Nationality must be between 2 and 100 characters")
        String nationality
) {
}