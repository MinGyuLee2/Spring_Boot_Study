package com.example.demo.domain.review.controller;

import com.example.demo.domain.review.dto.ReviewResponse;
import com.example.demo.domain.review.service.ReviewService;
import com.example.demo.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{reviewId}")
    public ApiResponse<ReviewResponse> getReview(@PathVariable Long reviewId) {
        return ApiResponse.onSuccess(reviewService.getReview(reviewId));
    }
}
