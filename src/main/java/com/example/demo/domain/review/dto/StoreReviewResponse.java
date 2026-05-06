package com.example.demo.domain.review.dto;

import java.time.LocalDateTime;

/**
 * 가게 리뷰 목록 화면의 개별 리뷰 응답 DTO입니다.
 */
public record StoreReviewResponse(
        Long reviewId,
        Long memberId,
        String nickname,
        Integer rating,
        String content,
        LocalDateTime createdAt
) {
}
