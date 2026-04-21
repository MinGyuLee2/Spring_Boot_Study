package com.example.demo.domain.review.repository;

import com.example.demo.domain.review.entity.ReviewPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ReviewPhoto 엔티티의 DB 접근을 담당하는 Repository입니다.
 */
public interface ReviewPhotoRepository extends JpaRepository<ReviewPhoto, Long> {
}
