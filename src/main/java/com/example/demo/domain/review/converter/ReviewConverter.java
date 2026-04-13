package com.example.demo.domain.review.converter;

import com.example.demo.domain.review.dto.ReviewResponse;
import com.example.demo.domain.review.entity.Review;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ReviewConverter {

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
