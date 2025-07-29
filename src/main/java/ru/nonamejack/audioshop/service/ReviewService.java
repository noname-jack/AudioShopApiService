package ru.nonamejack.audioshop.service;

import ru.nonamejack.audioshop.dto.product.ReviewDto;
import ru.nonamejack.audioshop.dto.request.CreateReviewRequest;
import ru.nonamejack.audioshop.exception.custom.NotFoundException;
import ru.nonamejack.audioshop.mapper.ProductMapper;
import ru.nonamejack.audioshop.mapper.ReviewMapper;
import ru.nonamejack.audioshop.model.Product;
import ru.nonamejack.audioshop.model.ReviewProduct;
import ru.nonamejack.audioshop.repository.ProductRepository;
import ru.nonamejack.audioshop.repository.ReviewProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final MessageService messageService;
    private final ReviewProductRepository reviewProductRepository;

    private final ReviewMapper reviewMapper;

    public ReviewService(ProductMapper productMapper, ProductRepository productRepository, MessageService messageService, ReviewProductRepository reviewProductRepository, ReviewMapper reviewMapper) {
        this.productMapper = productMapper;

        this.productRepository = productRepository;
        this.messageService = messageService;
        this.reviewProductRepository = reviewProductRepository;
        this.reviewMapper = reviewMapper;
    }

    @Transactional
    public ReviewDto createReview(Integer productId, CreateReviewRequest createReviewRequest){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(messageService.getMessage("error.product.not_found", productId)));
        ReviewProduct newReviewProduct = reviewMapper.toModel(createReviewRequest);
        newReviewProduct.setProduct(product);
        reviewProductRepository.save(newReviewProduct);
        return productMapper.toReviewDto(newReviewProduct);
    }

    @Transactional(readOnly = true)
    public List<ReviewDto> getReviewsByProduct(Integer productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(messageService.getMessage("error.product.not_found", productId)));

        List<ReviewProduct> reviewProductList = product.getProductReviewList();

        return reviewProductList.stream()
                .map(productMapper::toReviewDto)
                .toList();
    }
}
