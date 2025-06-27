package org.example.hbookstore.author.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

/**
 * DTO for {@link org.example.hbookstore.author.domain.Author}
 */
public record CreateAuthorRequest(
        @NotBlank(message = "First name is required")
        @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
        String firstName,
        @NotBlank(message = "Last name is required")
        @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
        String lastName,
        @NotBlank(message = "Nationality is required")
        @Size(min = 2, max = 100, message = "Nationality must be between 2 and 100 characters")
        String nationality
) {
}