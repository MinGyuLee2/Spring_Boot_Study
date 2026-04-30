package com.example.demo.domain.review.dto;

import java.util.List;

/**
 * 특정 가게에 작성된 리뷰 목록과 페이지 정보를 함께 담는 응답 DTO입니다.
 */
public record StoreReviewPageResponse(
        List<StoreReviewResponse> reviews,
        int page,
        int size,
        long totalElements,
        int totalPages
) {
}
