package org.example.hbookstore.customer.domain;

import org.example.hbookstore.customer.domain.enums.CustomerCategory;
import org.example.hbookstore.customer.domain.enums.CustomerStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long>, PagingAndSortingRepository<Customer, Long> {
    boolean existsByUserId(Long userId);
    boolean existsByEmail(String email);
    Page<Customer> findByStatus(CustomerStatus status, Pageable pageable);
    Page<Customer> findByCategory(CustomerCategory category, Pageable pageable);
    Page<Customer> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
            String firstName,
            String lastName,
            Pageable pageable
    );
}
