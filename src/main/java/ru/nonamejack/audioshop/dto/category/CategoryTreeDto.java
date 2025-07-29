package ru.nonamejack.audioshop.dto.category;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

@Data
@JsonIdentityInfo(
        generator  = ObjectIdGenerators.PropertyGenerator.class,
        property   = "categoryId"
)
@Schema(description = "Узел дерева категорий")
public class CategoryTreeDto {
    @Schema(description = "ID категории", example = "1")
    private Integer categoryId;

    @Schema(description = "Название категории", example = "Динамики")
    private String name;
    private String description;
    private String imgPath;
    @Schema(
            description = "Вложенные подкатегории",
            type        = "array",
            implementation = CategoryTreeDto.class
    )
    private List<CategoryTreeDto> children;
} 