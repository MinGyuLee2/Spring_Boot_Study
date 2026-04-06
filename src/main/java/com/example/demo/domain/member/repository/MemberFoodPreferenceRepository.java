package com.example.demo.domain.member.repository;

import com.example.demo.domain.member.entity.MemberFoodPreference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberFoodPreferenceRepository extends JpaRepository<MemberFoodPreference, Long> {
}
