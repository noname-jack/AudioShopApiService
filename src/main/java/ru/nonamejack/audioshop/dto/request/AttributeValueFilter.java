package ru.nonamejack.audioshop.dto.request;

import ru.nonamejack.audioshop.dto.RangeDto;
import ru.nonamejack.audioshop.model.FilterType;
import ru.nonamejack.audioshop.model.ValueType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class AttributeValueFilter {
    @NotNull
    private Integer attributeId;

    @NotNull()
    private FilterType filterType;

    @NotNull()
    private ValueType valueType;

    private List<String> stringValues;
    private List<Double>  doubleValues;
    private List<Boolean> booleanValues;
    private RangeDto rangeValue;
    }
