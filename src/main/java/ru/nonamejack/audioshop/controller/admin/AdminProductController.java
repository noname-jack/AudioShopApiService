package ru.nonamejack.audioshop.controller.admin;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nonamejack.audioshop.dto.ApiResponse;
import ru.nonamejack.audioshop.dto.product.AdminProductSummaryDto;
import ru.nonamejack.audioshop.dto.request.ProductFilterAdminRequest;
import ru.nonamejack.audioshop.service.ProductService;

@RestController
@RequestMapping("/admin/products")
@Validated
public class AdminProductController {

    private final ProductService productService;

    public AdminProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public ApiResponse<PagedModel<AdminProductSummaryDto>> getAllProducts(
            ProductFilterAdminRequest productFilterAdminRequest,
            @PageableDefault(size = 20, sort = "price") Pageable pageable){
        return ApiResponse.success(new PagedModel<>
                (productService.getAllProductsWithAdmin(productFilterAdminRequest,pageable)));

    }

}
