package ru.nonamejack.audioshop.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.nonamejack.audioshop.dto.RangeDto;

import java.util.List;

@Data
@Builder
public class ProductFilterAdminRequest {

    private Integer categoryId;
    private List<Integer> brandIds;
    private Boolean active;
    private String name;

}
