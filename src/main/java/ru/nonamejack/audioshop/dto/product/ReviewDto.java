package ru.nonamejack.audioshop.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private Integer reviewId;
    private Double rating;
    private String senderName;
    private String advantages;
    private String disadvantages;
    private String comment;
    private Date reviewDate;
}
