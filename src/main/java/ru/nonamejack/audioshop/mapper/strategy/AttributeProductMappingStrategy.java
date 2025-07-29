package ru.nonamejack.audioshop.mapper.strategy;

import ru.nonamejack.audioshop.dto.attribute.AttributeDto;
import ru.nonamejack.audioshop.model.ProductAttribute;

public interface AttributeProductMappingStrategy {
    boolean supports(ProductAttribute attribute);

    AttributeDto toDto(ProductAttribute attribute);
}
