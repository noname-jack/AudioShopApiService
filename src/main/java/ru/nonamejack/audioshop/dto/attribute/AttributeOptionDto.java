package ru.nonamejack.audioshop.dto.attribute;

import ru.nonamejack.audioshop.dto.RangeDto;
import ru.nonamejack.audioshop.model.FilterType;
import ru.nonamejack.audioshop.model.ValueType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttributeOptionDto {
    private Integer attributeId;
    private String attributeName;
    private FilterType filterType;
    private ValueType valueType;
    private List<String> availableValuesString;
    private List<Double> availableValuesDouble;
    private RangeDto numericRange;
}
