package com.example.demo.domain.mission.repository;

import com.example.demo.domain.mission.entity.MemberMission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {
}
