package org.example.hbookstore.book.mapping;

import java.time.Instant;
import org.example.hbookstore.book.api.dto.BookResponse;
import org.example.hbookstore.book.api.dto.CreateBookRequest;
import org.example.hbookstore.book.api.dto.UpdateBookRequest;
import org.example.hbookstore.book.domain.Book;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(Instant.now())")
    @Mapping(target = "updatedAt", expression = "java(Instant.now())")
    Book toEntity(CreateBookRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Book updateEntity(@MappingTarget Book book, UpdateBookRequest request);

    BookResponse toResponse(Book book);

    @AfterMapping
    default void touchUpdatedAt(@MappingTarget Book book) {
        book.setUpdatedAt(Instant.now());
    }
}
