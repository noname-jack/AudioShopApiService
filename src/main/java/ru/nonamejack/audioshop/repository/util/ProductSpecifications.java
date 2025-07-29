package ru.nonamejack.audioshop.repository.util;

import ru.nonamejack.audioshop.dto.RangeDto;
import ru.nonamejack.audioshop.model.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class ProductSpecifications {

    public static Specification<Product> byCategory(Integer categoryId) {
        return (root, query, cb) -> {
            if (categoryId == null) {
                return cb.conjunction(); // всегда true, фильтр не применяется
            }
            return cb.equal(root.get("category").get("categoryId"), categoryId);
        };
    }

    public static Specification<Product> byBrands(List<Integer> selectedBrandIds) {
        return (root, query, cb) -> {
            if (selectedBrandIds == null || selectedBrandIds.isEmpty()) {
                return cb.conjunction();
            }
            return root.get("brand").get("id").in(selectedBrandIds);
        };
    }

    public static Specification<Product> byPriceRange(RangeDto priceRangeDto) {
        return (root, query, cb) -> {
            if (priceRangeDto == null || priceRangeDto.getMin() == null || priceRangeDto.getMax() == null) {
                return cb.conjunction();
            }
            return cb.between(root.get("price"), priceRangeDto.getMin(), priceRangeDto.getMax());
        };
    }

    public static Specification<Product> isActive(Boolean active) {
        return (root, query, cb) -> {
            if (active == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("active"), active);
        };
    }

    public static Specification<Product> nameContains(String name) {
        return (root, query, cb) -> {
            if (name == null || name.isBlank()) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

}


