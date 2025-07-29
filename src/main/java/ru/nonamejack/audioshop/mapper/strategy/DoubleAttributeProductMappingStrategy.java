package ru.nonamejack.audioshop.mapper.strategy;

import ru.nonamejack.audioshop.dto.attribute.AttributeDto;
import ru.nonamejack.audioshop.mapper.AttributeMapper;
import ru.nonamejack.audioshop.model.ProductAttribute;
import ru.nonamejack.audioshop.model.ValueType;
import org.springframework.stereotype.Component;


@Component
public class DoubleAttributeProductMappingStrategy implements AttributeProductMappingStrategy {
    private final AttributeMapper attributeMapper;

    public DoubleAttributeProductMappingStrategy(AttributeMapper attributeMapper) {
        this.attributeMapper = attributeMapper;
    }

    @Override
    public boolean supports(ProductAttribute attribute) {
        return  attribute
                .getAttribute()
                .getValueType()
                .equals(ValueType.DOUBLE) && attribute.getDoubleValue() != null;
    }

    @Override
    public AttributeDto toDto(ProductAttribute attribute) {
        return attributeMapper.toDoubleDto(attribute);
    }
}
