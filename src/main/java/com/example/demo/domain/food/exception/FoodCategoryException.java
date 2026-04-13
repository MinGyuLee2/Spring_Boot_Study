package com.example.demo.domain.food.exception;

import com.example.demo.global.exception.DomainException;

public class FoodCategoryException extends DomainException {

    public FoodCategoryException(FoodCategoryErrorCode errorCode) {
        super(errorCode);
    }
}
