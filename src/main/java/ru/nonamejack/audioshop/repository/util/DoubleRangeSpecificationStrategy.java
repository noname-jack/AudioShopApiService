package ru.nonamejack.audioshop.repository.util;

import ru.nonamejack.audioshop.dto.RangeDto;
import ru.nonamejack.audioshop.dto.request.AttributeValueFilter;
import ru.nonamejack.audioshop.model.FilterType;
import ru.nonamejack.audioshop.model.Product;
import ru.nonamejack.audioshop.model.ProductAttribute;
import ru.nonamejack.audioshop.model.ValueType;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class DoubleRangeSpecificationStrategy implements AttributeFilterSpecificationStrategy {
    @Override
    public boolean supports(AttributeValueFilter filter) {
        return filter.getFilterType() == FilterType.RANGE
                && filter.getValueType() == ValueType.DOUBLE
                && filter.getRangeValue() != null
                && filter.getRangeValue().getMin() != null
                && filter.getRangeValue().getMax() != null;
    }

    @Override
    public Specification<Product> toSpecification(AttributeValueFilter filter) {
        return (root, query, cb) -> {
            Join<Product, ProductAttribute> join =
                    root.join("productAttributeList", JoinType.INNER);
            Predicate byId = cb.equal(
                    join.get("attribute").get("attributeId"),
                    filter.getAttributeId()
            );
            RangeDto r = filter.getRangeValue();
            return cb.and(
                    byId,
                    cb.between(join.get("doubleValue"), r.getMin(), r.getMax())
            );
        };
    }
}
