package org.example.hbookstore.customer.api.dto;

import jakarta.validation.constraints.*;
import org.example.hbookstore.customer.domain.enums.CustomerCategory;
import org.example.hbookstore.customer.domain.enums.CustomerStatus;

public record UpdateCustomerRequest(
        @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
        String firstName,

        @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
        String lastName,

        @Email(message = "Email should be valid")
        String email,

        @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number should be valid")
        String phoneNumber,

        CustomerCategory category,

        CustomerStatus status
) {
}
