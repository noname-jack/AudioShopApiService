package ru.nonamejack.audioshop.dto.attribute;

import ru.nonamejack.audioshop.model.ValueType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class BooleanAttributeDto extends AttributeDto {
    private Boolean value;

}
