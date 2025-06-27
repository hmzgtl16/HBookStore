package org.example.hbookstore.customer.api;

import org.example.hbookstore.customer.api.dto.CreateCustomerRequest;
import org.example.hbookstore.customer.api.dto.CustomerResponse;
import org.example.hbookstore.customer.api.dto.UpdateCustomerRequest;
import org.example.hbookstore.customer.domain.enums.CustomerCategory;
import org.example.hbookstore.customer.domain.enums.CustomerStatus;
import org.example.hbookstore.customer.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<CustomerResponse> createCustomer(
            @PathVariable Long userId,
            @RequestBody CreateCustomerRequest request
    ) {
        CustomerResponse response = customerService.createCustomer(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable Long id) {
        CustomerResponse response = customerService.getCustomer(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable Long id,
            @RequestBody UpdateCustomerRequest request
    ) {
        CustomerResponse response = customerService.updateCustomer(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<Page<CustomerResponse>> getAllCustomers(
            @PageableDefault(size = 20) Pageable pageable
    ) {
        Page<CustomerResponse> response = customerService.getAllCustomers(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<CustomerResponse>> getCustomersByStatus(
            @PathVariable CustomerStatus status,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        Page<CustomerResponse> response = customerService.getCustomersByStatus(status, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<Page<CustomerResponse>> getCustomersByCategory(
            @PathVariable CustomerCategory category,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        Page<CustomerResponse> response = customerService.getCustomersByCategory(category, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CustomerResponse>> searchCustomers(
            @RequestParam String query,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        Page<CustomerResponse> response = customerService.searchCustomers(query, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
