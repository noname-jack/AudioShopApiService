package ru.nonamejack.audioshop.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Builder
public class BrandDtoRequest {
    @NotNull
    @NotEmpty
    @NotBlank
    private String name;
    private String shortDescription;
    private String description;
}
