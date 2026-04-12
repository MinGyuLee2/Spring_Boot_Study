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

    private final FoodCategoryService foodCategoryService;

    @GetMapping("/{foodCategoryId}")
    public ApiResponse<FoodCategoryResponse> getFoodCategory(@PathVariable Long foodCategoryId) {
        return ApiResponse.onSuccess(foodCategoryService.getFoodCategory(foodCategoryId));
    }
}
