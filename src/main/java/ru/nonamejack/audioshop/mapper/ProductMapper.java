package ru.nonamejack.audioshop.mapper;

import ru.nonamejack.audioshop.dto.attribute.AttributeDto;
import ru.nonamejack.audioshop.dto.product.AdminProductSummaryDto;
import ru.nonamejack.audioshop.dto.product.ProductDetailsDto;
import ru.nonamejack.audioshop.dto.product.ProductSummaryDto;
import ru.nonamejack.audioshop.dto.product.ReviewDto;
import ru.nonamejack.audioshop.mapper.factory.AttributeMapperFactory;
import ru.nonamejack.audioshop.model.*;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.nonamejack.audioshop.service.ImageService;

import java.util.*;
import java.util.stream.Collectors;


@Mapper(componentModel="spring")
public abstract class ProductMapper {

    @Autowired
    protected AttributeMapperFactory attributeMapperFactory;
    @Autowired
    protected ImageService imageService;

    @Named("toImagePath")
    public String productImageToString(ProductImage image) {
        return imageService.buildImageUrl(image.getImageUrl());
    }


    @Mapping(target = "reviewDate", source = "reviewDate")
    public abstract ReviewDto toReviewDto(ReviewProduct review);


    @Mapping(target = "brandName", source = "brand.name")
    @Mapping(target = "characteristics", expression = "java(mapRequiredAttributes(product))")
    @Mapping(target = "mainImage", expression = "java(imageService.buildImageUrl(product.getMainImage()))")
    public abstract ProductSummaryDto toSummary(Product product);

    @Mapping(target = "brandName", source = "brand.name")
    @Mapping(target = "mainImage", expression = "java(imageService.buildImageUrl(product.getMainImage()))")
    public abstract AdminProductSummaryDto toAdminProductSummaryDto(Product product);


    @Mapping(target = "brandName", source = "brand.name")
    @Mapping(target = "brandId", source = "brand.id")
    @Mapping(target = "imagesPathList", source = "productImages", qualifiedByName = "toImagePath")
    @Mapping(target = "reviews", source = "productReviewList")
    @Mapping(target = "attributes", source = "productAttributeList",  qualifiedByName = "mapAttributesProduct")
    @Mapping(target = "mainImage", expression = "java(imageService.buildImageUrl(product.getMainImage()))")
    public  abstract ProductDetailsDto toDetails(Product product);


    protected List<AttributeDto> mapRequiredAttributes(Product product) {
        if (product == null
                || product.getCategory() == null
                || product.getCategory().getCategoryAttributeList() == null) {
            return Collections.emptyList();
        }

        // ID атрибутов, у которых isRequired == true
        Set<Integer> requiredIds = product.getCategory()
                .getCategoryAttributeList().stream()
                .filter(CategoryAttribute::isRequired)
                .map(ca -> ca.getAttribute().getAttributeId())
                .collect(Collectors.toSet());

        // Фильтр product.getProductAttributeList() по обязательным ID
        List<ProductAttribute> filtered = Optional.ofNullable(product.getProductAttributeList())
                .orElse(Collections.emptyList())
                .stream()
                .filter(pa -> requiredIds.contains(pa.getAttribute().getAttributeId()))
                .toList();

         return attributeMapperFactory.mapAttributes(filtered);
    }
    @Named("mapAttributesProduct")
    protected List<AttributeDto> mapAllAttributes(List<ProductAttribute> list) {
        if (list == null) {
            return Collections.emptyList();
        }
        return attributeMapperFactory.mapAttributes(list);
    }
}
