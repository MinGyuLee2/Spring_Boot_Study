package com.example.demo.domain.mission.repository;

import com.example.demo.domain.mission.entity.Mission;
import com.example.demo.domain.mission.enums.MissionStatus;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Mission 엔티티의 DB 접근을 담당하는 Repository입니다.
 */
public interface MissionRepository extends JpaRepository<Mission, Long> {

    // 메서드 이름만으로 status 조건의 select 쿼리를 만드는 Spring Data JPA 쿼리 메서드입니다.
    List<Mission> findByStatus(MissionStatus status);

    /**
     * 특정 지역의 활성 미션 중 회원이 아직 참여하지 않은 미션을 조회합니다.
     *
     * <p>{@code @EntityGraph}는 응답 변환에 필요한 store와 region을 함께 조회해서
     * 지연 로딩으로 인한 추가 쿼리를 줄여줍니다.</p>
     */
    @EntityGraph(attributePaths = {"store", "store.region"})
    @Query(
            value = """
                    select mission
                    from Mission mission
                    join mission.store store
                    join store.region region
                    where mission.status = :status
                      and region.id = :regionId
                      and not exists (
                          select 1
                          from MemberMission memberMission
                          where memberMission.mission = mission
                            and memberMission.member.id = :memberId
                      )
                    """,
            countQuery = """
                    select count(mission)
                    from Mission mission
                    join mission.store store
                    join store.region region
                    where mission.status = :status
                      and region.id = :regionId
                      and not exists (
                          select 1
                          from MemberMission memberMission
                          where memberMission.mission = mission
                            and memberMission.member.id = :memberId
                      )
                    """
    )
    Page<Mission> findAvailableMissions(
            @Param("memberId") Long memberId,
            @Param("regionId") Long regionId,
            @Param("status") MissionStatus status,
            Pageable pageable
    );
}
