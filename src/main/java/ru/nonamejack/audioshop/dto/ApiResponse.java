package ru.nonamejack.audioshop.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class ApiResponse<T> {

    private String message;
    private T data;
    private String error;
    private HttpStatus status;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>("success", data, null, HttpStatus.OK);
    }

    public static <T> ApiResponse<T> success(T data, HttpStatus status) {
        return new ApiResponse<>("success", data, null, status);
    }

    public static <T> ApiResponse<T> error(String errorMessage, HttpStatus status) {
        return new ApiResponse<>("error", null, errorMessage, status);
    }
}
