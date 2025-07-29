package ru.nonamejack.audioshop.dto.category;

import lombok.Data;

@Data
public class CategoryDto {
    private Integer categoryId;
    private Integer parentCategoryId;
    private String name;
    private String description;
    private String imgPath;
} 