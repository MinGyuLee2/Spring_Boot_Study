package com.example.demo.domain.food.controller;

import com.example.demo.domain.food.dto.FoodCategoryResponse;
import com.example.demo.domain.food.service.FoodCategoryService;
import com.example.demo.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/food-categories")
public class FoodCategoryController {

    // 음식 카테고리 조회 로직은 Service 계층에 위임합니다.
    private final FoodCategoryService foodCategoryService;

    /**
     * 음식 카테고리 단건 정보를 조회합니다.
     */
    @GetMapping("/{foodCategoryId}")
    public ApiResponse<FoodCategoryResponse> getFoodCategory(@PathVariable Long foodCategoryId) {
        return ApiResponse.onSuccess(foodCategoryService.getFoodCategory(foodCategoryId));
    }
}
