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

    @Id
    @Column(name = "review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(optional = false)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @OneToMany(mappedBy = "review")
    private List<ReviewPhoto> reviewPhotos = new ArrayList<>();

    public static Review create(Member member, Store store, Integer rating, String content) {
        Review review = new Review();
        review.member = member;
        review.store = store;
        review.rating = rating;
        review.content = content;
        return review;
    }

    public void addReviewPhoto(ReviewPhoto reviewPhoto) {
        this.reviewPhotos.add(reviewPhoto);
    }
}
