package com.example.demo.domain.mission.repository;

import com.example.demo.domain.mission.entity.MemberMission;
import com.example.demo.domain.mission.enums.MemberMissionStatus;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {

    long countByMember_IdAndStatus(Long memberId, MemberMissionStatus status);

    @EntityGraph(attributePaths = {"mission", "mission.store"})
    Page<MemberMission> findByMember_IdAndStatus(Long memberId, MemberMissionStatus status, Pageable pageable);

    Optional<MemberMission> findByIdAndMember_Id(Long memberMissionId, Long memberId);
}
