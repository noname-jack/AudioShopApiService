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
public class ProductAttributeKey implements Serializable {
    private Integer productId;
    private Integer attributeId;
}
