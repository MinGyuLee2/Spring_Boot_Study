package com.example.demo.domain.food.exception;

import com.example.demo.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum FoodCategoryErrorCode implements BaseErrorCode {
    FOOD_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "FOOD404", "Food category not found");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
