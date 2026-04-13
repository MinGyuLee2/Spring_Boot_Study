package com.example.demo.domain.food.converter;

import com.example.demo.domain.food.dto.FoodCategoryResponse;
import com.example.demo.domain.food.entity.FoodCategory;
import org.springframework.stereotype.Component;

@Component
public class FoodCategoryConverter {

    public FoodCategoryResponse toResponse(FoodCategory foodCategory) {
        return new FoodCategoryResponse(
                foodCategory.getId(),
                foodCategory.getName()
        );
    }
}
