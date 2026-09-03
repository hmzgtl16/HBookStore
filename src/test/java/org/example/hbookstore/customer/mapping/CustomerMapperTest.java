package org.example.hbookstore.customer.mapping;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import org.example.hbookstore.customer.api.dto.CreateCustomerRequest;
import org.example.hbookstore.customer.api.dto.CustomerResponse;
import org.example.hbookstore.customer.api.dto.UpdateCustomerRequest;
import org.example.hbookstore.customer.domain.Customer;
import org.example.hbookstore.customer.domain.enums.CustomerCategory;
import org.example.hbookstore.customer.domain.enums.CustomerStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("CustomerMapper Unit Tests")
class CustomerMapperTest {

    private CustomerMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = CustomerMapper.INSTANCE;
    }

    @Test
    void toEntity_mapsFieldsAndDefaultsStatusToActive_withNullId() {
        CreateCustomerRequest request =
                new CreateCustomerRequest(
                        "Ada",
                        "Lovelace",
                        "ada@example.com",
                        "+15551234567",
                        CustomerCategory.PREMIUM);

        Customer customer = mapper.toEntity(request);

        assertThat(customer.getId()).isNull();
        assertThat(customer.getFirstName()).isEqualTo("Ada");
        assertThat(customer.getLastName()).isEqualTo("Lovelace");
        assertThat(customer.getEmail()).isEqualTo("ada@example.com");
        assertThat(customer.getPhoneNumber()).isEqualTo("+15551234567");
        assertThat(customer.getStatus())
                .isEqualTo(CustomerStatus.ACTIVE); // always ACTIVE on create
        assertThat(customer.getCategory()).isEqualTo(CustomerCategory.PREMIUM);
        assertThat(customer.getCreatedAt()).isNotNull().isBeforeOrEqualTo(Instant.now());
        assertThat(customer.getUpdatedAt()).isNotNull().isBeforeOrEqualTo(Instant.now());
    }

    @Test
    void updateEntity_appliesOnlyNonNullFields_andPreservesOthers() {
        Instant originalCreatedAt = Instant.now().minusSeconds(3600);
        Customer customer =
                new Customer(
                        1L,
                        "Ada",
                        "Lovelace",
                        "ada@example.com",
                        "+15551234567",
                        CustomerStatus.ACTIVE,
                        CustomerCategory.REGULAR,
                        originalCreatedAt,
                        originalCreatedAt,
                        100L);

        UpdateCustomerRequest request =
                new UpdateCustomerRequest("Augusta", null, null, null, null, null);

        mapper.updateEntity(customer, request);

        assertThat(customer.getFirstName()).isEqualTo("Augusta"); // updated
        assertThat(customer.getLastName()).isEqualTo("Lovelace"); // preserved
        assertThat(customer.getEmail()).isEqualTo("ada@example.com"); // preserved
        assertThat(customer.getPhoneNumber()).isEqualTo("+15551234567"); // preserved
        assertThat(customer.getStatus()).isEqualTo(CustomerStatus.ACTIVE); // preserved
        assertThat(customer.getCategory()).isEqualTo(CustomerCategory.REGULAR); // preserved
        assertThat(customer.getCreatedAt()).isEqualTo(originalCreatedAt); // untouched
        assertThat(customer.getUserId()).isEqualTo(100L); // untouched, not in update DTO
    }

    @Test
    void updateEntity_updatesStatusAndCategoryWhenProvided() {
        Customer customer =
                new Customer(
                        1L,
                        "Ada",
                        "Lovelace",
                        "ada@example.com",
                        "+15551234567",
                        CustomerStatus.ACTIVE,
                        CustomerCategory.REGULAR,
                        Instant.now(),
                        Instant.now(),
                        100L);

        UpdateCustomerRequest request =
                new UpdateCustomerRequest(
                        null,
                        null,
                        null,
                        null,
                        CustomerCategory.ENTERPRISE,
                        CustomerStatus.SUSPENDED);

        mapper.updateEntity(customer, request);

        assertThat(customer.getCategory()).isEqualTo(CustomerCategory.ENTERPRISE);
        assertThat(customer.getStatus()).isEqualTo(CustomerStatus.SUSPENDED);
    }

    @Test
    void updateEntity_alwaysTouchesUpdatedAt_evenWhenRequestIsAllNull() {
        Instant originalUpdatedAt = Instant.now().minusSeconds(3600);
        Customer customer =
                new Customer(
                        1L,
                        "Ada",
                        "Lovelace",
                        "ada@example.com",
                        "+15551234567",
                        CustomerStatus.ACTIVE,
                        CustomerCategory.REGULAR,
                        originalUpdatedAt,
                        originalUpdatedAt,
                        100L);

        UpdateCustomerRequest request =
                new UpdateCustomerRequest(null, null, null, null, null, null);

        mapper.updateEntity(customer, request);

        assertThat(customer.getUpdatedAt()).isAfter(originalUpdatedAt);
    }

    @Test
    void updateEntity_doesNotChangeId() {
        Customer customer =
                new Customer(
                        42L,
                        "Ada",
                        "Lovelace",
                        "ada@example.com",
                        "+15551234567",
                        CustomerStatus.ACTIVE,
                        CustomerCategory.REGULAR,
                        Instant.now(),
                        Instant.now(),
                        100L);

        UpdateCustomerRequest request =
                new UpdateCustomerRequest("Augusta", null, null, null, null, null);

        mapper.updateEntity(customer, request);

        assertThat(customer.getId()).isEqualTo(42L);
    }

    @Test
    void toResponse_mapsAllFieldsIncludingUserId() {
        Instant createdAt = Instant.now().minusSeconds(100);
        Instant updatedAt = Instant.now();
        Customer customer =
                new Customer(
                        7L,
                        "Ada",
                        "Lovelace",
                        "ada@example.com",
                        "+15551234567",
                        CustomerStatus.ACTIVE,
                        CustomerCategory.PREMIUM,
                        createdAt,
                        updatedAt,
                        100L);

        CustomerResponse response = mapper.toResponse(customer);

        assertThat(response.id()).isEqualTo(7L);
        assertThat(response.firstName()).isEqualTo("Ada");
        assertThat(response.lastName()).isEqualTo("Lovelace");
        assertThat(response.email()).isEqualTo("ada@example.com");
        assertThat(response.phoneNumber()).isEqualTo("+15551234567");
        assertThat(response.category()).isEqualTo(CustomerCategory.PREMIUM);
        assertThat(response.status()).isEqualTo(CustomerStatus.ACTIVE);
        assertThat(response.createdAt()).isEqualTo(createdAt);
        assertThat(response.updatedAt()).isEqualTo(updatedAt);
        assertThat(response.userId()).isEqualTo(100L);
    }
}
