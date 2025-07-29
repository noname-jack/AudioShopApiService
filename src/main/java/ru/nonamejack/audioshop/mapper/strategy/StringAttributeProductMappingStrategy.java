package ru.nonamejack.audioshop.mapper.strategy;

import ru.nonamejack.audioshop.dto.attribute.AttributeDto;
import ru.nonamejack.audioshop.mapper.AttributeMapper;
import ru.nonamejack.audioshop.model.ProductAttribute;
import ru.nonamejack.audioshop.model.ValueType;
import org.springframework.stereotype.Component;

@Component
public class StringAttributeProductMappingStrategy implements AttributeProductMappingStrategy {
    private final AttributeMapper attributeMapper;

    public StringAttributeProductMappingStrategy(AttributeMapper attributeMapper) {
        this.attributeMapper = attributeMapper;
    }

    @Override
    public boolean supports(ProductAttribute attribute) {
        return attribute
                .getAttribute()
                .getValueType()
                .equals(ValueType.STRING)
                &&
                attribute
                        .getStringValue() != null
                && !attribute.getStringValue().isEmpty();
    }

    @Override
    public AttributeDto toDto(ProductAttribute attribute) {
        return attributeMapper.toStringDto(attribute);
    }
}
