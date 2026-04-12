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

public interface MissionRepository extends JpaRepository<Mission, Long> {

    List<Mission> findByStatus(MissionStatus status);

    @EntityGraph(attributePaths = {"store", "store.region"})
    @Query("""
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
            """)
    Page<Mission> findAvailableMissions(
            @Param("memberId") Long memberId,
            @Param("regionId") Long regionId,
            @Param("status") MissionStatus status,
            Pageable pageable
    );
}
