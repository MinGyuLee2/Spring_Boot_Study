package com.example.demo.domain.region.repository;

import com.example.demo.domain.region.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {
}
