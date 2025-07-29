package ru.nonamejack.audioshop.dto.product;

import ru.nonamejack.audioshop.dto.attribute.AttributeDto;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Краткое представление товара")
public class ProductSummaryDto {

    @Schema(description = "ID товара", example = "1001")
    private Integer productId;

    @Schema(description = "Название бренда", example = "Sony")
    private String brandName;

    @Schema(description = "Название товара", example = "Ultra")
    private String name;

    @Schema(description = "Цена товара", example = "1299.99")
    private BigDecimal price;

    @Schema(description = "Остаток на складе", example = "42")
    private Integer stock;

    @Schema(description = "URL основной картинки", example = "https://cdn.example.com/img/1001.png")
    private String mainImage;

    @ArraySchema(
            arraySchema   = @Schema(description = "Список характеристик товара"),
            schema        = @Schema(implementation = AttributeDto.class)
    )
    private List<AttributeDto> characteristics;
}