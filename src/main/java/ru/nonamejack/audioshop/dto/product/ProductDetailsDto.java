package ru.nonamejack.audioshop.dto.product;

import ru.nonamejack.audioshop.dto.attribute.AttributeDto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class ProductDetailsDto {
    private Integer productId;

    private String brandName;

    private Integer brandId;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer stock;

    private String mainImage;

    private List<String> imagesPathList;

    private List<AttributeDto> attributes;

    private List<ReviewDto> reviews;
}
