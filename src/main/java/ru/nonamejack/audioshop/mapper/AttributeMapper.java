package ru.nonamejack.audioshop.mapper;

import ru.nonamejack.audioshop.dto.attribute.*;
import ru.nonamejack.audioshop.model.CategoryAttribute;
import ru.nonamejack.audioshop.model.ProductAttribute;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AttributeMapper {

    // === DoubleAttributeDto ===
    @Mapping(source = "attribute.attributeId", target = "attributeId")
    @Mapping(source = "attribute.name",        target = "attributeName")
    @Mapping(source = "attribute.valueType", target = "valueType")
    AttributeDto toBaseDto(ProductAttribute pa);
    // === DoubleAttributeDto ===
    @InheritConfiguration(name = "toBaseDto")
    @Mapping(source = "doubleValue",             target = "value")
    DoubleAttributeDto toDoubleDto(ProductAttribute pa);

    // === StringAttributeDto ===
    @InheritConfiguration(name = "toBaseDto")
    @Mapping(source = "stringValue",          target = "value")
    StringAttributeDto toStringDto(ProductAttribute pa);

    // === BooleanAttributeDto ===
    @InheritConfiguration(name = "toBaseDto")
    @Mapping(source = "booleanValue",         target = "value")
    BooleanAttributeDto toBooleanDto(ProductAttribute pa);

    @Mapping(source = "attribute.attributeId", target = "attributeId")
    @Mapping(source = "attribute.name",        target = "attributeName")
    @Mapping(source = "attribute.valueType", target = "valueType")
    @Mapping(source = "attribute.filterType", target = "filterType")
    @Mapping(source = "required", target = "required")
    @Mapping(source = "useFilter", target = "useFilter")
    AttributeCategoryDto toAttributeCategory(CategoryAttribute categoryAttribute);

    List<AttributeCategoryDto> toAttributeCategoryList(List<CategoryAttribute> categoryAttributeList);

    @Mapping(target = "availableValuesString", ignore = true)
    StringListAttributeFilterDto toStringFilter(AttributeCategoryDto base);

    @Mapping(target = "availableValuesDouble", ignore = true)
    DoubleListAttributeFilterDto toDoubleListFilter(AttributeCategoryDto base);

    @Mapping(target = "minValue", ignore = true)
    @Mapping(target = "maxValue", ignore = true)
    DoubleRangeAttributeDto toRangeFilter(AttributeCategoryDto base);

    @Mapping(target = "availableValuesBoolean", ignore = true)
    BooleanListAttributeDto toBooleanListFilter(AttributeCategoryDto base);
}
