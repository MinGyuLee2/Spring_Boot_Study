package com.example.demo.domain.review.converter;

import com.example.demo.domain.review.dto.ReviewResponse;
import com.example.demo.domain.review.entity.Review;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ReviewConverter {

    /**
     * 리뷰 엔티티를 응답 DTO로 변환합니다.
     *
     * <p>ReviewPhoto 엔티티 목록은 클라이언트가 쓰기 쉬운 URL 문자열 목록으로 변환합니다.</p>
     */
    public ReviewResponse toResponse(Review review) {
        List<String> photoUrls = review.getReviewPhotos().stream()
                .map(photo -> photo.getPhotoUrl())
                .toList();

        return new ReviewResponse(
                review.getId(),
                review.getMember().getId(),
                review.getStore().getId(),
                review.getRating(),
                review.getContent(),
                photoUrls
        );
    }
}
