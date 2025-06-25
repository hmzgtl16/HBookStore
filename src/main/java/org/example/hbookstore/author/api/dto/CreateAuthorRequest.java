package org.example.hbookstore.author.api.dto;

import java.time.LocalDate;

/**
 * DTO for {@link org.example.hbookstore.author.domain.Author}
 */
public record CreateAuthorRequest(
        String firstName,
        String lastName,
        String biography,
        LocalDate birthDate,
        String nationality
) {
}