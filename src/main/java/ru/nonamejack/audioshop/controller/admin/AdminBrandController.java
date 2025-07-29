package ru.nonamejack.audioshop.controller.admin;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.nonamejack.audioshop.dto.ApiResponse;
import ru.nonamejack.audioshop.dto.brand.BrandDto;
import ru.nonamejack.audioshop.dto.request.BrandDtoRequest;
import ru.nonamejack.audioshop.service.BrandService;

import java.util.List;

@RestController
@RequestMapping("/admin/brands")
@Validated
public class AdminBrandController {

    private final BrandService brandService;

    public AdminBrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping()
    public ApiResponse<List<BrandDto>> getAllBrands(@RequestParam String name){
        return ApiResponse.success(brandService.getAllBrands(name));
    }

    @GetMapping("/{id}")
    public ApiResponse<BrandDto> getBrandById(@PathVariable Integer id) {
        return ApiResponse.success(brandService.getBrandById(id));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Boolean> deleteBrandById(@PathVariable Integer id){
        brandService.deleteBrandById(id);
        return ApiResponse.success(true);
    }

    @PutMapping("/{id}")
    public ApiResponse<BrandDto> updateBrandById(@PathVariable Integer id, @RequestBody @Valid BrandDtoRequest brandDto){
        return ApiResponse.success(brandService.updateBrandById(id, brandDto));
    }

    @PostMapping()
    public ApiResponse<BrandDto> createBrand(@RequestBody @Valid BrandDtoRequest brandDto) {
        return ApiResponse.success(brandService.createBrand(brandDto));
    }

    @PatchMapping("/image/{id}")
    public ApiResponse<BrandDto> addImageToBrand(@RequestParam MultipartFile file, @PathVariable Integer id){
        return ApiResponse.success(brandService.addImageToBrand(file, id));
    }



}
