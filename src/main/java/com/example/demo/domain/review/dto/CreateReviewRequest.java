package com.example.demo.domain.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * 리뷰 생성 요청 DTO입니다.
 */
public record CreateReviewRequest(
        // 별점은 1점 이상 5점 이하만 허용합니다.
        @NotNull
        @Min(1)
        @Max(5)
        Integer rating,

        // 리뷰 본문은 비어 있을 수 없습니다.
        @NotBlank
        String content,

        // 첨부 이미지 URL 목록입니다. 사진이 없으면 null 또는 빈 리스트일 수 있습니다.
        List<String> imageUrls,

        // 리뷰 작성 권한 확인에 사용할 회원 미션 ID입니다.
        @NotNull
        Long memberMissionId
) {
}
