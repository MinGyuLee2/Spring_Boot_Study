package com.example.demo.domain.food.dto;

/**
 * 음식 카테고리 단건 조회 응답 DTO입니다.
 */
public record FoodCategoryResponse(
        Long foodCategoryId,
        String name
) {
}
