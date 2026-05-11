package com.example.demo.domain.review.dto;

import java.time.LocalDateTime;

/**
 * 내가 작성한 리뷰 목록의 개별 리뷰 응답 DTO입니다.
 */
public record MyReviewResponse(
        Long reviewId,
        Long storeId,
        String storeName,
        Integer rating,
        String content,
        LocalDateTime createdAt
) {
}
