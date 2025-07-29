package ru.nonamejack.audioshop.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateReviewRequest {
    @NotNull(message = "Рейтинг обязателен")
    @DecimalMin(value = "0.0", message = "Рейтинг не может быть меньше {value}")
    @DecimalMax(value = "5.0", message = "Рейтинг не может быть больше {value}")
    private Double rating;

    @NotBlank(message = "Имя отправителя обязательно")
    @Size(max = 100, message = "Имя отправителя не может превышать {max} символов")
    private String senderName;

    @NotBlank
    @Size(max = 1000, message = "Достоинства не могут превышать {max} символов")
    private String advantages;

    @NotBlank
    @Size(max = 1000, message = "Недостатки не могут превышать {max} символов")
    private String disadvantages;

    @Size(max = 2000, message = "Комментарий не может превышать {max} символов")
    private String comment;
}
