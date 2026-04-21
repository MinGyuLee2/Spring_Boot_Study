package com.example.demo.domain.food.repository;

import com.example.demo.domain.food.entity.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * FoodCategory 엔티티의 DB 접근을 담당하는 Repository입니다.
 */
public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Long> {
}
