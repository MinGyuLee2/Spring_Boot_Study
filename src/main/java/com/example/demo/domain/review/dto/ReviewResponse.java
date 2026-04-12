package com.example.demo.domain.review.dto;

import java.util.List;

public record ReviewResponse(
        Long reviewId,
        Long memberId,
        Long storeId,
        Integer rating,
        String content,
        List<String> photoUrls
) {
}
