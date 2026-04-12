package com.example.demo.domain.store.controller;

import com.example.demo.domain.review.dto.CreateReviewRequest;
import com.example.demo.domain.review.dto.ReviewResponse;
import com.example.demo.domain.review.service.ReviewService;
import com.example.demo.global.apiPayload.ApiResponse;
import com.example.demo.global.apiPayload.code.GeneralSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stores")
public class StoreController {

    private final ReviewService reviewService;

    @PostMapping(value = "/{storeId}/reviews", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<ReviewResponse>> createReview(
            @RequestHeader(value = "X-Member-Id", defaultValue = "1") Long memberId,
            @PathVariable Long storeId,
            @Valid @RequestBody CreateReviewRequest request
    ) {
        ReviewResponse response = reviewService.createReview(memberId, storeId, request);
        return ResponseEntity.status(GeneralSuccessCode.CREATED.getHttpStatus())
                .body(ApiResponse.onSuccess(GeneralSuccessCode.CREATED, response));
    }
}
