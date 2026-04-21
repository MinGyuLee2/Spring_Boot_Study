package com.example.demo.domain.review.entity;

import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.store.entity.Store;
import com.example.demo.global.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "review")
public class Review extends BaseTimeEntity {

    // 리뷰 테이블의 기본 키입니다.
    @Id
    @Column(name = "review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 리뷰 작성자입니다.
    @ManyToOne(optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // 리뷰가 작성된 가게입니다.
    @ManyToOne(optional = false)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    // 별점입니다. 요청 DTO에서 1점 이상 5점 이하로 검증합니다.
    @Column(nullable = false)
    private Integer rating;

    // 리뷰 본문입니다. 길이가 길 수 있어 TEXT 타입으로 저장합니다.
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    // 리뷰에 첨부된 사진 목록입니다.
    @OneToMany(mappedBy = "review")
    private List<ReviewPhoto> reviewPhotos = new ArrayList<>();

    /**
     * 리뷰 생성에 필요한 값을 한곳에서 설정하는 정적 팩토리 메서드입니다.
     */
    public static Review create(Member member, Store store, Integer rating, String content) {
        Review review = new Review();
        review.member = member;
        review.store = store;
        review.rating = rating;
        review.content = content;
        return review;
    }

    /**
     * 리뷰와 리뷰 사진의 객체 그래프를 메모리상에서도 연결합니다.
     */
    public void addReviewPhoto(ReviewPhoto reviewPhoto) {
        this.reviewPhotos.add(reviewPhoto);
    }
}
