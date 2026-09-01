package org.example.hbookstore.customer.api.dto;

import java.time.Instant;
import org.example.hbookstore.customer.domain.enums.CustomerCategory;
import org.example.hbookstore.customer.domain.enums.CustomerStatus;

public record CustomerResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        CustomerCategory category,
        CustomerStatus status,
        Instant createdAt,
        Instant updatedAt,
        Long userId) {}
