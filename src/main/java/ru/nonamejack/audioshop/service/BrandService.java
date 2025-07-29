package ru.nonamejack.audioshop.service;

import org.springframework.web.multipart.MultipartFile;
import ru.nonamejack.audioshop.dto.brand.BrandDto;
import ru.nonamejack.audioshop.dto.request.BrandDtoRequest;
import ru.nonamejack.audioshop.exception.custom.FileCreateException;
import ru.nonamejack.audioshop.exception.custom.NotFoundException;
import ru.nonamejack.audioshop.exception.custom.ResourceCreationException;
import ru.nonamejack.audioshop.mapper.BrandMapper;
import ru.nonamejack.audioshop.model.Brand;
import ru.nonamejack.audioshop.repository.BrandRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;
    private final MessageService messageService;

    private final ImageService imageService;
    public BrandService(BrandRepository brandRepository, BrandMapper brandMapper, MessageService messageService, ImageService imageService) {
        this.brandRepository = brandRepository;
        this.brandMapper = brandMapper;
        this.messageService = messageService;
        this.imageService = imageService;
    }

    public List<BrandDto> getAllBrands(){
        return brandMapper.toBrandDtoList(brandRepository.findAll());
    }

    public List<BrandDto> getAllBrands(String name){
        return brandMapper.toBrandDtoList(brandRepository.findByNameContainingIgnoreCase(name));
    }

    public BrandDto getBrandById(Integer brandId){
        Brand brand = brandRepository.findById(brandId).orElseThrow(
                ()->new NotFoundException(messageService.getMessage("error.brand.not_found",brandId)));
        return brandMapper.toBrandDto(brand);
    }

    public BrandDto createBrand(BrandDtoRequest brandDto) {
        if(brandRepository.findByName(brandDto.getName()).isPresent()){
            throw new ResourceCreationException("Бренд с таким именем уже существует");
        }
        Brand newBrand = brandMapper.toBrand(brandDto);
        Brand savedBrand = brandRepository.save(newBrand);
        return brandMapper.toBrandDto(savedBrand);
    }

    public void deleteBrandById(Integer id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Бренд с id " + id + " не найден"));
        brandRepository.delete(brand);
    }

    public BrandDto updateBrandById(Integer id, BrandDtoRequest brandDto) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Бренд с id " + id + " не найден"));

        Optional<Brand> brandWithSameName = brandRepository.findByName(brandDto.getName());
        if (brandWithSameName.isPresent() && !brandWithSameName.get().getId().equals(id)) {
            throw new IllegalArgumentException("Бренд с таким именем уже существует");
        }

        brand.setName(brandDto.getName());
        brand.setShortDescription(brandDto.getShortDescription());
        brand.setDescription(brandDto.getDescription());
        brandRepository.save(brand);
        return brandMapper.toBrandDto(brand);
    }

    public BrandDto addImageToBrand(MultipartFile file, Integer id){
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Бренд с таким id не найден"));
        String imagePath = imageService.saveImage(file, "brands");
        if (imageService.imageExists(imagePath)){
            try {
                brand.setImagePath(imagePath);
                brandRepository.save(brand);
                return brandMapper.toBrandDto(brand);
            }
            catch (Exception ex){
                imageService.deleteImage(imagePath);
                throw new ResourceCreationException("Не удалось сохранить изображение");
            }
        }
        else{
            throw new FileCreateException("Не удалось сохранить файл");
        }
    }
}
