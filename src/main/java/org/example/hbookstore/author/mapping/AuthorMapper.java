package org.example.hbookstore.author.mapping;

import java.time.Instant;
import org.example.hbookstore.author.api.dto.AuthorResponse;
import org.example.hbookstore.author.api.dto.CreateAuthorRequest;
import org.example.hbookstore.author.api.dto.UpdateAuthorRequest;
import org.example.hbookstore.author.domain.Author;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthorMapper {

    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(Instant.now())")
    @Mapping(target = "updatedAt", expression = "java(Instant.now())")
    Author toEntity(CreateAuthorRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Author updateEntity(@MappingTarget Author author, UpdateAuthorRequest request);

    AuthorResponse toResponse(Author author);

    @AfterMapping
    default void touchUpdatedAt(@MappingTarget Author author) {
        author.setUpdatedAt(Instant.now());
    }
}
