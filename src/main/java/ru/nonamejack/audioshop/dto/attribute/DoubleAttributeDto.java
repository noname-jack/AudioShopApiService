package ru.nonamejack.audioshop.dto.attribute;

import ru.nonamejack.audioshop.model.ValueType;
import lombok.*;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@Getter
@Setter
public class DoubleAttributeDto extends AttributeDto{
    private Integer value;
}
