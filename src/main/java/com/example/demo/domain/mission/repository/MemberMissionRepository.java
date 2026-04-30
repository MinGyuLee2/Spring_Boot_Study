package com.example.demo.domain.mission.repository;

import com.example.demo.domain.mission.entity.MemberMission;
import com.example.demo.domain.mission.enums.MemberMissionStatus;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * MemberMission 엔티티의 DB 접근을 담당하는 Repository입니다.
 */
public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {

    // 특정 회원이 특정 상태로 가진 미션 개수를 계산합니다.
    long countByMember_IdAndStatus(Long memberId, MemberMissionStatus status);

    /**
     * 현재 회원이 진행 중이거나 완료한 미션을 상태별로 페이징 조회합니다.
     *
     * <p>과제 조건에 맞춰 페이징 목록 조회는 JPQL {@code @Query}로 명시합니다.</p>
     */
    @EntityGraph(attributePaths = {"mission", "mission.store"})
    @Query(
            value = """
                    select memberMission
                    from MemberMission memberMission
                    join memberMission.member member
                    join memberMission.mission mission
                    join mission.store store
                    where member.id = :memberId
                      and memberMission.status = :status
                    """,
            countQuery = """
                    select count(memberMission)
                    from MemberMission memberMission
                    join memberMission.member member
                    where member.id = :memberId
                      and memberMission.status = :status
                    """
    )
    Page<MemberMission> findByMember_IdAndStatus(
            @Param("memberId") Long memberId,
            @Param("status") MemberMissionStatus status,
            Pageable pageable
    );

    // 회원 본인의 미션인지 확인하기 위해 미션 ID와 회원 ID를 함께 조건으로 사용합니다.
    @EntityGraph(attributePaths = {"member", "mission", "mission.store"})
    Optional<MemberMission> findByIdAndMember_Id(Long memberMissionId, Long memberId);
}
