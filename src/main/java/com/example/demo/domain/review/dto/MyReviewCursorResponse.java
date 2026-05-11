package com.example.demo.domain.review.dto;

import java.util.List;

/**
 * 내가 작성한 리뷰 목록의 커서 기반 페이지 응답 DTO입니다.
 */
public record MyReviewCursorResponse(
        List<MyReviewResponse> data,
        Boolean hasNext,
        String nextCursor,
        Integer pageSize
) {
}
