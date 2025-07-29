package ru.nonamejack.audioshop.service;

import ru.nonamejack.audioshop.dto.attribute.AttributeCategoryDto;
import ru.nonamejack.audioshop.dto.attribute.AttributeDto;
import ru.nonamejack.audioshop.exception.custom.NotFoundException;
import ru.nonamejack.audioshop.mapper.AttributeMapper;
import ru.nonamejack.audioshop.model.Category;
import ru.nonamejack.audioshop.model.CategoryAttribute;
import ru.nonamejack.audioshop.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AttributeService {

    private final CategoryRepository categoryRepository;
    private final MessageService messageService;
    private final AttributeMapper attributeMapper;

    public AttributeService(CategoryRepository categoryRepository, MessageService messageService, AttributeMapper attributeMapper) {
        this.categoryRepository = categoryRepository;
        this.messageService = messageService;
        this.attributeMapper = attributeMapper;
    }


    @Transactional(readOnly = true)
    public List<AttributeCategoryDto> getAttributeCategory(Integer categoryId){
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(messageService.getMessage("error.category.not_found", categoryId)));
        List<CategoryAttribute> categoryAttribute = category.getCategoryAttributeList();
        return  attributeMapper.toAttributeCategoryList(categoryAttribute);
    }
}
