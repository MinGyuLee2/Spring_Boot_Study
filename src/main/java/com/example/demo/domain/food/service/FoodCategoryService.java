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

    // 음식 카테고리 엔티티를 조회하는 Repository입니다.
    private final FoodCategoryRepository foodCategoryRepository;

    // FoodCategory 엔티티를 응답 DTO로 변환합니다.
    private final FoodCategoryConverter foodCategoryConverter;

    /**
     * 음식 카테고리 ID로 단건 조회합니다.
     */
    public FoodCategoryResponse getFoodCategory(Long foodCategoryId) {
        return foodCategoryRepository.findById(foodCategoryId)
                .map(foodCategoryConverter::toResponse)
                .orElseThrow(() -> new FoodCategoryException(FoodCategoryErrorCode.FOOD_CATEGORY_NOT_FOUND));
    }
}
