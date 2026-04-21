package com.example.demo.domain.region.repository;

import com.example.demo.domain.region.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Region 엔티티의 DB 접근을 담당하는 Repository입니다.
 */
public interface RegionRepository extends JpaRepository<Region, Long> {
}
