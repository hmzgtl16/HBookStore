package org.example.hbookstore.customer.service;

import org.example.hbookstore.customer.api.dto.CreateCustomerRequest;
import org.example.hbookstore.customer.api.dto.CustomerResponse;
import org.example.hbookstore.customer.api.dto.UpdateCustomerRequest;
import org.example.hbookstore.customer.domain.Customer;
import org.example.hbookstore.customer.domain.CustomerRepository;
import org.example.hbookstore.customer.domain.enums.CustomerCategory;
import org.example.hbookstore.customer.domain.enums.CustomerStatus;
import org.example.hbookstore.customer.mapping.CustomerMapper;
import org.example.hbookstore.shared.error.EntityNotFoundException;
import org.example.hbookstore.shared.error.InvalidRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerServiceImpl implements  CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(
            CustomerRepository customerRepository,
            CustomerMapper customerMapper
    ) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Transactional
    @Override
    public CustomerResponse createCustomer(Long userId, CreateCustomerRequest request) {
        validateCustomer(userId, request);
        Customer customer = customerMapper.toEntity(request);
        customer.setUserId(userId);
        return customerMapper.toResponse(customerRepository.save(customer));
    }

    @Transactional(readOnly = true)
    @Override
    public CustomerResponse getCustomer(Long id) {
        return customerRepository.findById(id)
                .map(customerMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));
    }

    @Transactional
    @Override
    public CustomerResponse updateCustomer(Long id, UpdateCustomerRequest request) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));
        customer = customerMapper.updateEntity(customer, request);
        return customerMapper.toResponse(customerRepository.save(customer));
    }

    @Transactional
    @Override
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new EntityNotFoundException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<CustomerResponse> getAllCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable)
                .map(customerMapper::toResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<CustomerResponse> getCustomersByStatus(CustomerStatus status, Pageable pageable) {
        return customerRepository.findByStatus(status, pageable)
                .map(customerMapper::toResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<CustomerResponse> getCustomersByCategory(CustomerCategory category, Pageable pageable) {
        return customerRepository.findByCategory(category, pageable)
                .map(customerMapper::toResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<CustomerResponse> searchCustomers(String query, Pageable pageable) {
        return customerRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(query, query, pageable)
                .map(customerMapper::toResponse);
    }

    private void validateCustomer(Long userId, CreateCustomerRequest request) {
        if (customerRepository.existsByUserId(userId)) {
            throw new InvalidRequestException("Customer already exists for customerId: " + userId);
        }
        if (customerRepository.existsByEmail(request.email())) {
            throw new InvalidRequestException("Email already exists: ${request.email}");
        }
    }
}
