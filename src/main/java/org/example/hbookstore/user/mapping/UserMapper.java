package org.example.hbookstore.user.mapping;

import java.time.Instant;
import org.example.hbookstore.user.api.dto.CreateUserRequest;
import org.example.hbookstore.user.api.dto.UpdateUserRequest;
import org.example.hbookstore.user.api.dto.UserResponse;
import org.example.hbookstore.user.domain.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", constant = "")
    @Mapping(target = "status", expression = "java(UserStatus.ACTIVE)")
    @Mapping(target = "createdAt", expression = "java(Instant.now())")
    @Mapping(target = "updatedAt", expression = "java(Instant.now())")
    User toEntity(CreateUserRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User updateEntity(@MappingTarget User user, UpdateUserRequest request);

    UserResponse toResponse(User user);

    @AfterMapping
    default void touchUpdatedAt(@MappingTarget User user) {
        user.setUpdatedAt(Instant.now());
    }
}
