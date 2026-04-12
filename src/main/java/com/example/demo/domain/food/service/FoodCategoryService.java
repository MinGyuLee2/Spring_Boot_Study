package com.example.demo.domain.food.service;

import com.example.demo.domain.food.converter.FoodCategoryConverter;
import com.example.demo.domain.food.dto.FoodCategoryResponse;
import com.example.demo.domain.food.exception.FoodCategoryErrorCode;
import com.example.demo.domain.food.exception.FoodCategoryException;
import com.example.demo.domain.food.repository.FoodCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FoodCategoryService {

    private final FoodCategoryRepository foodCategoryRepository;
    private final FoodCategoryConverter foodCategoryConverter;

    public FoodCategoryResponse getFoodCategory(Long foodCategoryId) {
        return foodCategoryRepository.findById(foodCategoryId)
                .map(foodCategoryConverter::toResponse)
                .orElseThrow(() -> new FoodCategoryException(FoodCategoryErrorCode.FOOD_CATEGORY_NOT_FOUND));
    }
}
