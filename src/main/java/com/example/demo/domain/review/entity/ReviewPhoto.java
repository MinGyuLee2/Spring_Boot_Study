package com.example.demo.domain.review.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "review_photo")
public class ReviewPhoto {

    // 리뷰 사진 테이블의 기본 키입니다.
    @Id
    @Column(name = "review_photo_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 실제 이미지 파일이 저장된 위치의 URL입니다.
    @Column(name = "photo_url", nullable = false, columnDefinition = "TEXT")
    private String photoUrl;

    // 사진이 속한 리뷰입니다. 여러 사진이 하나의 리뷰에 속할 수 있습니다.
    @ManyToOne(optional = false)
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    /**
     * 리뷰 사진 엔티티를 생성합니다.
     */
    public static ReviewPhoto create(Review review, String photoUrl) {
        ReviewPhoto reviewPhoto = new ReviewPhoto();
        reviewPhoto.review = review;
        reviewPhoto.photoUrl = photoUrl;
        return reviewPhoto;
    }
}
