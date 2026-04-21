package com.example.demo.domain.review.dto;

import java.util.List;

/**
 * 리뷰 단건 조회와 리뷰 생성 결과에 사용하는 응답 DTO입니다.
 */
public record ReviewResponse(
        Long reviewId,
        Long memberId,
        Long storeId,
        Integer rating,
        String content,
        List<String> photoUrls
) {
}
