package ru.nonamejack.audioshop.mapper.factory;

import ru.nonamejack.audioshop.dto.attribute.AttributeDto;
import ru.nonamejack.audioshop.mapper.strategy.AttributeProductMappingStrategy;
import ru.nonamejack.audioshop.model.ProductAttribute;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AttributeMapperFactory {
    private final List<AttributeProductMappingStrategy> strategies;

    public AttributeMapperFactory(List<AttributeProductMappingStrategy> strategies) {

        this.strategies = strategies;
    }
    public AttributeDto mapAttribute(ProductAttribute productAttribute){
        return strategies.stream()
                .filter(strategies -> strategies.supports(productAttribute))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No mapper for " + productAttribute.getAttribute().getValueType()))
                .toDto(productAttribute);
    }

    public List<AttributeDto> mapAttributes(List<ProductAttribute> list) {
        if (list == null) {
            return Collections.emptyList();
        }
        return list.stream()
                .sorted(Comparator
                        .comparing((ProductAttribute pa) -> pa.getAttribute().getValueType())
                        .thenComparing(pa -> pa.getAttribute().getName(), String.CASE_INSENSITIVE_ORDER))
                .map(this::mapAttribute)
                .toList();
    }
}
