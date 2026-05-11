package com.example.demo.domain.review.controller;

import com.example.demo.domain.review.dto.MyReviewCursorRequest;
import com.example.demo.domain.review.dto.MyReviewCursorResponse;
import com.example.demo.domain.review.dto.ReviewResponse;
import com.example.demo.domain.review.service.ReviewService;
import com.example.demo.global.apiPayload.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    // 리뷰 조회 로직은 Service 계층에 위임합니다.
    private final ReviewService reviewService;

    /**
     * 내가 작성한 리뷰 목록을 커서 기반으로 조회합니다.
     */
    @PostMapping(value = "/me", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<MyReviewCursorResponse> getMyReviews(
            @Valid @RequestBody MyReviewCursorRequest request
    ) {
        return ApiResponse.onSuccess(reviewService.getMyReviews(request));
    }

    /**
     * 리뷰 단건 정보를 조회합니다.
     */
    @GetMapping("/{reviewId}")
    public ApiResponse<ReviewResponse> getReview(@PathVariable Long reviewId) {
        return ApiResponse.onSuccess(reviewService.getReview(reviewId));
    }
}
