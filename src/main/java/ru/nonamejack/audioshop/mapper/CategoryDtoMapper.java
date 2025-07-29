package ru.nonamejack.audioshop.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import ru.nonamejack.audioshop.dto.category.CategoryDto;
import ru.nonamejack.audioshop.dto.category.CategoryTreeDto;
import ru.nonamejack.audioshop.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nonamejack.audioshop.service.ImageService;

@Mapper(componentModel = "spring")
public abstract class CategoryDtoMapper {
    @Autowired
    protected ImageService imageService;

    @Mapping(target = "imgPath", expression = "java(imageService.buildImageUrl(category.getImgPath()))")
    public  abstract  CategoryDto categoryToCategoryDTO(Category category);

    @Mapping(target = "imgPath", expression = "java(imageService.buildImageUrl(category.getImgPath()))")

    @Mapping(target = "children", ignore = true)
    public  abstract  CategoryTreeDto categoryToCategoryTreeDTO(Category category);
}
