package org.example.hbookstore.customer.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import org.example.hbookstore.customer.domain.enums.CustomerCategory;
import org.example.hbookstore.customer.domain.enums.CustomerStatus;
import org.example.hbookstore.shared.AbstractDataJdbcTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@DisplayName("CustomerRepository Integration Tests")
class CustomerRepositoryTest extends AbstractDataJdbcTest {

    @Autowired private CustomerRepository customerRepository;

    private Customer newCustomer(
            String firstName,
            String lastName,
            String email,
            CustomerStatus status,
            CustomerCategory category) {
        return new Customer(
                null,
                firstName,
                lastName,
                email,
                "555-0100",
                status,
                category,
                Instant.now(),
                Instant.now(),
                null);
    }

    @Test
    void existsByUserId_returnsTrue_whenLinked() {
        Customer customer =
                new Customer(
                        null,
                        "Ada",
                        "Lovelace",
                        "ada@example.com",
                        "555-0100",
                        CustomerStatus.ACTIVE,
                        CustomerCategory.REGULAR,
                        Instant.now(),
                        Instant.now(),
                        3L);

        customerRepository.save(customer);

        assertThat(customerRepository.existsByUserId(3L)).isTrue();
    }

    @Test
    void existsByUserId_returnsFalse_whenNotLinked() {
        assertThat(customerRepository.existsByUserId(999L)).isFalse();
    }

    @Test
    void existsByEmail_returnsTrue_whenEmailPresent() {
        customerRepository.save(
                newCustomer(
                        "Ada",
                        "Lovelace",
                        "ada@example.com",
                        CustomerStatus.ACTIVE,
                        CustomerCategory.REGULAR));

        assertThat(customerRepository.existsByEmail("ada@example.com")).isTrue();
    }

    @Test
    void existsByEmail_returnsFalse_whenEmailAbsent() {
        assertThat(customerRepository.existsByEmail("nobody@example.com")).isFalse();
    }

    @Test
    void findByStatus_returnsOnlyMatchingCustomers() {
        customerRepository.save(
                newCustomer(
                        "Ada",
                        "Lovelace",
                        "ada@example.com",
                        CustomerStatus.ACTIVE,
                        CustomerCategory.REGULAR));
        customerRepository.save(
                newCustomer(
                        "Grace",
                        "Hopper",
                        "grace@example.com",
                        CustomerStatus.SUSPENDED,
                        CustomerCategory.REGULAR));

        Page<Customer> page =
                customerRepository.findByStatus(CustomerStatus.ACTIVE, PageRequest.of(0, 10));

        assertThat(page.getContent())
                .extracting(Customer::getFirstName)
                .containsExactlyInAnyOrder("Jane", "John", "Ada");
    }

    @Test
    void findByCategory_returnsOnlyMatchingCustomers() {
        customerRepository.save(
                newCustomer(
                        "Ada",
                        "Lovelace",
                        "ada@example.com",
                        CustomerStatus.ACTIVE,
                        CustomerCategory.ENTERPRISE));
        customerRepository.save(
                newCustomer(
                        "Grace",
                        "Hopper",
                        "grace@example.com",
                        CustomerStatus.ACTIVE,
                        CustomerCategory.REGULAR));

        Page<Customer> page =
                customerRepository.findByCategory(
                        CustomerCategory.ENTERPRISE, PageRequest.of(0, 10));

        assertThat(page.getContent())
                .extracting(Customer::getFirstName)
                .containsExactly("John", "Ada");
    }

    @Test
    void search_matchesFirstOrLastName_caseInsensitively() {
        customerRepository.save(
                newCustomer(
                        "Ada",
                        "Lovelace",
                        "ada@example.com",
                        CustomerStatus.ACTIVE,
                        CustomerCategory.REGULAR));
        customerRepository.save(
                newCustomer(
                        "Grace",
                        "Hopper",
                        "grace@example.com",
                        CustomerStatus.ACTIVE,
                        CustomerCategory.REGULAR));

        Page<Customer> byFirstName =
                customerRepository
                        .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
                                "ada", "ada", PageRequest.of(0, 10));
        assertThat(byFirstName.getContent())
                .extracting(Customer::getFirstName)
                .containsExactly("Ada");

        Page<Customer> byLastName =
                customerRepository
                        .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
                                "hopper", "hopper", PageRequest.of(0, 10));
        assertThat(byLastName.getContent())
                .extracting(Customer::getLastName)
                .containsExactly("Hopper");
    }

    @Test
    void search_returnsEmptyPage_whenNoMatch() {
        customerRepository.save(
                newCustomer(
                        "Ada",
                        "Lovelace",
                        "ada@example.com",
                        CustomerStatus.ACTIVE,
                        CustomerCategory.REGULAR));

        Page<Customer> page =
                customerRepository
                        .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
                                "zzz", "zzz", PageRequest.of(0, 10));

        assertThat(page.getContent()).isEmpty();
    }
}
