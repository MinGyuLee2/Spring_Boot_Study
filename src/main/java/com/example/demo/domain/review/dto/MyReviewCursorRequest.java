package com.example.demo.domain.review.dto;

import com.example.demo.domain.review.enums.ReviewSortType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * 내가 작성한 리뷰 목록 커서 기반 조회 요청 DTO입니다.
 */
public record MyReviewCursorRequest(
        @NotNull
        @Positive
        Long memberId,

        String cursor,

        @NotNull
        @Min(1)
        @Max(50)
        Integer size,

        @NotNull
        ReviewSortType sortType
) {
}
