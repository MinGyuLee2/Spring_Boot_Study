package com.example.demo.domain.review.repository;

import com.example.demo.domain.review.entity.ReviewPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewPhotoRepository extends JpaRepository<ReviewPhoto, Long> {
}
