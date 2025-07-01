package org.example.hbookstore.customer.service;

import org.example.hbookstore.customer.api.dto.CreateCustomerRequest;
import org.example.hbookstore.customer.api.dto.CustomerResponse;
import org.example.hbookstore.customer.api.dto.UpdateCustomerRequest;
import org.example.hbookstore.customer.domain.enums.CustomerCategory;
import org.example.hbookstore.customer.domain.enums.CustomerStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {
    CustomerResponse createCustomer(Long userId, CreateCustomerRequest request);
    CustomerResponse getCustomer(Long id);
    CustomerResponse updateCustomer(Long id, UpdateCustomerRequest request);
    void deleteCustomer(Long id);
    Page<CustomerResponse> getAllCustomers(Pageable pageable);
    Page<CustomerResponse> getCustomersByStatus(CustomerStatus status, Pageable pageable);
    Page<CustomerResponse> getCustomersByCategory(CustomerCategory category, Pageable pageable);
    Page<CustomerResponse> searchCustomers(String query, Pageable pageable);
}
