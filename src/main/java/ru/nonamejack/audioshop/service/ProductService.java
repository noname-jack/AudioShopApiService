package ru.nonamejack.audioshop.service;


import ru.nonamejack.audioshop.dto.RangeDto;
import ru.nonamejack.audioshop.dto.product.AdminProductSummaryDto;
import ru.nonamejack.audioshop.dto.request.AttributeValueFilter;
import ru.nonamejack.audioshop.dto.product.ProductDetailsDto;
import ru.nonamejack.audioshop.dto.request.ProductFilterAdminRequest;
import ru.nonamejack.audioshop.dto.request.ProductFilterRequest;
import ru.nonamejack.audioshop.dto.product.ProductSummaryDto;
import ru.nonamejack.audioshop.exception.custom.BadRequestException;
import ru.nonamejack.audioshop.exception.custom.NotFoundException;
import ru.nonamejack.audioshop.mapper.ProductMapper;
import ru.nonamejack.audioshop.repository.util.AttributeFilterSpecificationFactory;
import ru.nonamejack.audioshop.model.*;
import ru.nonamejack.audioshop.repository.ProductRepository;
import ru.nonamejack.audioshop.repository.util.ProductSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;



@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final MessageService messageService;
    private final ProductMapper productMapper;
    private final AttributeFilterSpecificationFactory filterFactory;

    public ProductService(ProductRepository productRepository, MessageService messageService, ProductMapper productMapper, AttributeFilterSpecificationFactory filterFactory) {
        this.productRepository = productRepository;
        this.messageService = messageService;
        this.productMapper = productMapper;
        this.filterFactory = filterFactory;
    }


    @Transactional(readOnly= true)
    public ProductSummaryDto getProductSummary(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(messageService.getMessage("error.product.not_found", productId)));
        return productMapper.toSummary(product);
    }
    @Transactional(readOnly= true)
    public ProductDetailsDto getProductDetails(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(messageService.getMessage("error.product.not_found", productId)));
       return productMapper.toDetails(product);
    }

    @Transactional(readOnly= true)
    public Page<ProductSummaryDto> findProducts(ProductFilterRequest filterRequest, Pageable pageableRequest) {
        // 1) Базовая спецификация — по выбранной категории
        Specification<Product> combinedSpecification =
                ProductSpecifications.byCategory(filterRequest.getCategoryId());

        combinedSpecification.and(ProductSpecifications.isActive(true));

        // 2) Добавляем фильтр по брендам, если они переданы
        if (!CollectionUtils.isEmpty(filterRequest.getBrandIds())) {
            combinedSpecification = combinedSpecification.and(
                    ProductSpecifications.byBrands(filterRequest.getBrandIds())
            );
        }
        // 3) Добавляем диапазон цены, если он есть
        if (filterRequest.getPriceRange() != null) {
            RangeDto priceRange = filterRequest.getPriceRange();
            Double minPrice = priceRange.getMin();
            Double maxPrice = priceRange.getMax();
            if(minPrice > maxPrice){
                throw new BadRequestException(messageService.getMessage("error.filter.price_min_max", minPrice, maxPrice));
            }
            else {
                combinedSpecification = combinedSpecification.and(ProductSpecifications.byPriceRange(priceRange));
            }
        }
        if (filterRequest.getAttributeFilters() != null){
            for (AttributeValueFilter f : filterRequest.getAttributeFilters()) {
                combinedSpecification = combinedSpecification.and(filterFactory.createSpecification(f));
            }
        }

        Page<Product> productPage = productRepository.findAll(combinedSpecification, pageableRequest);
        return productPage.map(productMapper::toSummary);
    }

    public Page<AdminProductSummaryDto> getAllProductsWithAdmin(ProductFilterAdminRequest productFilterAdminRequest, Pageable pageable) {
        Specification<Product> specification = Specification.where(ProductSpecifications.byCategory(productFilterAdminRequest.getCategoryId()))
                .and(ProductSpecifications.isActive(productFilterAdminRequest.getActive()))
                .and(ProductSpecifications.byBrands(productFilterAdminRequest.getBrandIds()))
                .and(ProductSpecifications.nameContains(productFilterAdminRequest.getName()));
        Page<Product> productPage = productRepository.findAll(specification, pageable);
        return productPage.map(productMapper::toAdminProductSummaryDto);
    }
}
