package org.example.hbookstore.customer.mapping;

import java.time.Instant;
import org.example.hbookstore.customer.api.dto.CreateCustomerRequest;
import org.example.hbookstore.customer.api.dto.CustomerResponse;
import org.example.hbookstore.customer.api.dto.UpdateCustomerRequest;
import org.example.hbookstore.customer.domain.Customer;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", expression = "java(CustomerStatus.ACTIVE)")
    @Mapping(target = "createdAt", expression = "java(Instant.now())")
    @Mapping(target = "updatedAt", expression = "java(Instant.now())")
    Customer toEntity(CreateCustomerRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Customer updateEntity(@MappingTarget Customer customer, UpdateCustomerRequest request);

    CustomerResponse toResponse(Customer customer);

    @AfterMapping
    default void touchUpdatedAt(@MappingTarget Customer customer) {
        customer.setUpdatedAt(Instant.now());
    }
}

