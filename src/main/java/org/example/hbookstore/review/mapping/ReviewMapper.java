package org.example.hbookstore.review.mapping;

import java.time.Instant;
import org.example.hbookstore.review.api.dto.CreateReviewRequest;
import org.example.hbookstore.review.api.dto.ReviewResponse;
import org.example.hbookstore.review.api.dto.UpdateReviewRequest;
import org.example.hbookstore.review.domain.Review;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReviewMapper {

    ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(Instant.now())")
    @Mapping(target = "updatedAt", expression = "java(Instant.now())")
    Review toEntity(CreateReviewRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "customerId", ignore = true)
    @Mapping(target = "bookId", ignore = true)
    Review updateEntity(@MappingTarget Review review, UpdateReviewRequest request);

    ReviewResponse toResponse(Review review);

    @AfterMapping
    default void applyRatingIfPresent(UpdateReviewRequest request, @MappingTarget Review review) {
        if (request.rating() != 0) {
            review.setRating(request.rating());
        }
    }

    @AfterMapping
    default void touchUpdatedAt(@MappingTarget Review review) {
        review.setUpdatedAt(Instant.now());
    }
}
