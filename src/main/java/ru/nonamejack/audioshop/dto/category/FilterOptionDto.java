package ru.nonamejack.audioshop.dto.category;

import ru.nonamejack.audioshop.dto.RangeDto;
import ru.nonamejack.audioshop.dto.attribute.AttributeCategoryDto;
import ru.nonamejack.audioshop.dto.attribute.AttributeOptionDto;
import ru.nonamejack.audioshop.dto.brand.BrandDto;
import ru.nonamejack.audioshop.model.Brand;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Опции фильтрации товаров по категории")
public class FilterOptionDto {
    @Schema(description = "Идентификатор категории", example = "123")
    private Integer categoryId;

    @ArraySchema(
            arraySchema = @Schema(description = "Список доступных брендов"),
            schema = @Schema(implementation = BrandDto.class)
    )
    private List<BrandDto> brandDtoList;

    @Schema(description = "Диапазон цен", implementation = RangeDto.class)
    private RangeDto priceRange;

    @ArraySchema(
            arraySchema = @Schema(description = "Список атрибутов категории"),
            schema = @Schema(implementation = AttributeCategoryDto.class)
    )
    private List<AttributeCategoryDto> attributes;
}
