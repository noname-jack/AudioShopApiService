package ru.nonamejack.audioshop.mapper.strategy;

import ru.nonamejack.audioshop.dto.attribute.AttributeCategoryDto;
import ru.nonamejack.audioshop.dto.attribute.BooleanListAttributeDto;
import ru.nonamejack.audioshop.mapper.AttributeMapper;
import ru.nonamejack.audioshop.model.FilterType;
import ru.nonamejack.audioshop.model.ValueType;
import ru.nonamejack.audioshop.repository.ProductAttributeRepository;
import ru.nonamejack.audioshop.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BooleanListAttributeFilterStrategy  implements AttributeFilterStrategy{
    private final ProductAttributeRepository paRepo;
    private final AttributeMapper mapper;

    public BooleanListAttributeFilterStrategy(ProductAttributeRepository paRepo, AttributeMapper mapper) {
        this.paRepo = paRepo;
        this.mapper = mapper;
    }

    @Override
    public boolean supports(AttributeCategoryDto base) {
        return base
                .getValueType()
                .equals(ValueType.BOOLEAN) && base.getFilterType().equals(FilterType.BOOLEAN);
    }

    @Override
    public AttributeCategoryDto build(AttributeCategoryDto base, Integer categoryId) {
        List<Boolean> values = paRepo.findDistinctBooleanValues(categoryId, base.getAttributeId());
        BooleanListAttributeDto dto = mapper.toBooleanListFilter(base);
        dto.setAvailableValuesBoolean(values);
        return dto;
    }
}
