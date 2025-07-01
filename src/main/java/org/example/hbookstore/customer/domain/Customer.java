package org.example.hbookstore.customer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.hbookstore.customer.domain.enums.CustomerCategory;
import org.example.hbookstore.customer.domain.enums.CustomerStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customers")
public class Customer {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    private CustomerStatus status;
    private CustomerCategory category;

    private Instant createdAt;
    private Instant updatedAt;

    Long userId;

    public Customer(
            Long id,
            String firstName,
            String lastName,
            String email,
            String phoneNumber,
            CustomerStatus status,
            CustomerCategory category,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
