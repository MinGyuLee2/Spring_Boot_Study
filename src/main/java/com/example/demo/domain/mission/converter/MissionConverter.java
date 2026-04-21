package com.example.demo.domain.mission.converter;

import com.example.demo.domain.mission.dto.AvailableMissionResponse;
import com.example.demo.domain.mission.dto.MissionResponse;
import com.example.demo.domain.mission.entity.Mission;
import org.springframework.stereotype.Component;

@Component
public class MissionConverter {

    /**
     * 미션 엔티티를 기본 미션 조회 응답으로 변환합니다.
     */
    public MissionResponse toResponse(Mission mission) {
        return new MissionResponse(
                mission.getId(),
                mission.getStore().getId(),
                mission.getTitle(),
                mission.getDescription(),
                mission.getRewardPoint(),
                mission.getStatus()
        );
    }

    /**
     * 도전 가능한 미션 목록 응답으로 변환합니다.
     *
     * <p>목록 화면에서 가게와 지역 정보까지 보여줄 수 있도록 연관 엔티티 값을 함께 담습니다.</p>
     */
    public AvailableMissionResponse toAvailableMissionResponse(Mission mission) {
        return new AvailableMissionResponse(
                mission.getId(),
                mission.getTitle(),
                mission.getDescription(),
                mission.getRewardPoint(),
                mission.getStore().getId(),
                mission.getStore().getName(),
                mission.getStore().getCategory(),
                mission.getStore().getRegion().getId(),
                mission.getStore().getRegion().getName()
        );
    }
}
