package ru.nonamejack.audioshop.service;

import ru.nonamejack.audioshop.dto.RangeDto;
import ru.nonamejack.audioshop.dto.attribute.AttributeCategoryDto;
import ru.nonamejack.audioshop.dto.category.CategoryDto;
import ru.nonamejack.audioshop.dto.category.CategoryTreeDto;
import ru.nonamejack.audioshop.dto.category.FilterOptionDto;
import ru.nonamejack.audioshop.exception.custom.NotFoundException;
import ru.nonamejack.audioshop.mapper.BrandMapper;
import ru.nonamejack.audioshop.mapper.CategoryDtoMapper;
import ru.nonamejack.audioshop.mapper.factory.AttributeFilterFactory;
import ru.nonamejack.audioshop.model.Brand;
import ru.nonamejack.audioshop.model.Category;
import ru.nonamejack.audioshop.repository.BrandRepository;
import ru.nonamejack.audioshop.repository.CategoryRepository;
import ru.nonamejack.audioshop.repository.ProductRepository;
import ru.nonamejack.audioshop.util.IRange;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service

public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryDtoMapper categoryDtoMapper;
    private final MessageService messageService;
    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;
    private final AttributeService attributeService;
    private final AttributeFilterFactory attributeFilterFactory;
    private final BrandMapper brandMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryDtoMapper categoryDtoMapper, MessageService messageService, BrandRepository brandRepository, ProductRepository productRepository, AttributeService attributeService, AttributeFilterFactory attributeFilterFactory, BrandMapper brandMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryDtoMapper = categoryDtoMapper;
        this.messageService = messageService;
        this.brandRepository = brandRepository;
        this.productRepository = productRepository;
        this.attributeService = attributeService;
        this.attributeFilterFactory = attributeFilterFactory;
        this.brandMapper = brandMapper;
    }

    public List<CategoryTreeDto> getCategoryTree() {
        List<Category> allCategories = categoryRepository.findAll();
        Map<Integer, List<Category>> parentIdToChildren = allCategories.stream()
                .collect(Collectors.groupingBy(category -> 
                    category.getParentCategoryId() != null ? category.getParentCategoryId() : 0));
        return buildCategoryTree(parentIdToChildren, 0);
    }

    public CategoryTreeDto getCategoryTreeById(Integer id) {
        Category rootCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(messageService.getMessage("error.category.not_found", id)));
        return buildCategoryBranch(rootCategory);
    }

    private List<CategoryTreeDto> buildCategoryTree(Map<Integer, List<Category>> parentIdToChildren, Integer parentId) {
        List<Category> children = parentIdToChildren.get(parentId);
        if (children == null) {
            return new ArrayList<>();
        }
        return children.stream()
                .map(category -> {
                    CategoryTreeDto dto = categoryDtoMapper.categoryToCategoryTreeDTO(category);
                    dto.setChildren(buildCategoryTree(parentIdToChildren, category.getCategoryId()));
                    return dto;
                })
                .collect(Collectors.toList());
    }
    private CategoryTreeDto buildCategoryBranch(Category category) {
        CategoryTreeDto dto = categoryDtoMapper.categoryToCategoryTreeDTO(category);
        List<Category> children = categoryRepository.findByParentCategoryId(category.getCategoryId());

        List<CategoryTreeDto> childrenDtos = children.stream()
                .map(this::buildCategoryBranch)
                .collect(Collectors.toList());

        dto.setChildren(childrenDtos);
        return dto;
    }

    @Transactional(readOnly = true)
    public CategoryDto getCategoryById(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(messageService.getMessage("error.category.not_found", id)));
        return categoryDtoMapper.categoryToCategoryDTO(category);
    }
    public FilterOptionDto getFilterOptionToCategory(Integer id){
        List<Brand> brands = brandRepository.findDistinctBrands(id);
        IRange pr = productRepository.findPriceRange(id);
        List<AttributeCategoryDto>  attributeDtoList = attributeService.getAttributeCategory(id);
        attributeDtoList = attributeDtoList.stream()
                .filter(AttributeCategoryDto::isUseFilter)
                .map(dto -> attributeFilterFactory.build(dto, id))
                .toList();
        return FilterOptionDto.builder()
                .categoryId(id)
                .brandDtoList(brandMapper.toBrandDtoList(brands))
                .priceRange(RangeDto.builder().min(pr.getMinValue()).max(pr.getMaxValue()).build())
                .attributes(attributeDtoList).build();
    }

} 