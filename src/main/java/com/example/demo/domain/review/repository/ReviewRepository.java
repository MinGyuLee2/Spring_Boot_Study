package com.example.demo.domain.review.repository;

import com.example.demo.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Review 엔티티의 DB 접근을 담당하는 Repository입니다.
 */
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
