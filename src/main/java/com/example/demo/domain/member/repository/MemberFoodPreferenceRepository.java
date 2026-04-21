package com.example.demo.domain.member.repository;

import com.example.demo.domain.member.entity.MemberFoodPreference;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 회원과 음식 카테고리 선호 관계를 저장하고 조회하는 Repository입니다.
 */
public interface MemberFoodPreferenceRepository extends JpaRepository<MemberFoodPreference, Long> {
}
