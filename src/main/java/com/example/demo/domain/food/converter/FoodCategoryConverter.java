package com.example.demo.domain.food.converter;

import com.example.demo.domain.food.dto.FoodCategoryResponse;
import com.example.demo.domain.food.entity.FoodCategory;
import org.springframework.stereotype.Component;

@Component
public class FoodCategoryConverter {

    /**
     * 음식 카테고리 엔티티를 응답 DTO로 변환합니다.
     */
    public FoodCategoryResponse toResponse(FoodCategory foodCategory) {
        return new FoodCategoryResponse(
                foodCategory.getId(),
                foodCategory.getName()
        );
    }
}
