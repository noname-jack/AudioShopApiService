package ru.nonamejack.audioshop.mapper;

import ru.nonamejack.audioshop.dto.request.CreateReviewRequest;
import ru.nonamejack.audioshop.model.ReviewProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public  interface ReviewMapper {

    @Mapping(target = "reviewDate", expression = "java(new java.util.Date())")
    @Mapping(target = "reviewId", ignore = true)
    ReviewProduct toModel(CreateReviewRequest createReviewRequest);

}
