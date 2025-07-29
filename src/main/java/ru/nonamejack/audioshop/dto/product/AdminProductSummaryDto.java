package ru.nonamejack.audioshop.dto.product;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AdminProductSummaryDto {
    private Integer productId;

    private String brandName;

    private String name;

    private BigDecimal price;

    private Integer stock;

    private String mainImage;

    private Boolean active;
}
