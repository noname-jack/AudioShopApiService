package ru.nonamejack.audioshop.dto.brand;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BrandDto {
    private Integer id;
    private String name;
    private String imagePath;
    private String shortDescription;
    private String description;
}
