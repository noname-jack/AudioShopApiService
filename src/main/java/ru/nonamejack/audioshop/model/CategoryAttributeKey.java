package ru.nonamejack.audioshop.model;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class CategoryAttributeKey implements Serializable {
    private Integer categoryId;
    private Integer attributeId;
}
