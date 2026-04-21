package com.example.demo.domain.mission.repository;

import com.example.demo.domain.mission.entity.MemberMission;
import com.example.demo.domain.mission.enums.MemberMissionStatus;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * MemberMission 엔티티의 DB 접근을 담당하는 Repository입니다.
 */
public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {

    // 특정 회원이 특정 상태로 가진 미션 개수를 계산합니다.
    long countByMember_IdAndStatus(Long memberId, MemberMissionStatus status);

    // 응답 변환에 필요한 mission과 store를 함께 조회합니다.
    @EntityGraph(attributePaths = {"mission", "mission.store"})
    Page<MemberMission> findByMember_IdAndStatus(Long memberId, MemberMissionStatus status, Pageable pageable);

    // 회원 본인의 미션인지 확인하기 위해 미션 ID와 회원 ID를 함께 조건으로 사용합니다.
    Optional<MemberMission> findByIdAndMember_Id(Long memberMissionId, Long memberId);
}
