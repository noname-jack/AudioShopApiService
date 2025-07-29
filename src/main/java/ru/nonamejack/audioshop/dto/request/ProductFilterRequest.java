package ru.nonamejack.audioshop.dto.request;

import jakarta.validation.constraints.NotEmpty;
import ru.nonamejack.audioshop.dto.RangeDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


import java.util.List;

@Data
public class ProductFilterRequest {
    @NotNull(message = "обязательна категория")
    private Integer categoryId;

    private List<Integer> brandIds;

    private RangeDto priceRange;
    @Valid
    private List<AttributeValueFilter> attributeFilters;
}

