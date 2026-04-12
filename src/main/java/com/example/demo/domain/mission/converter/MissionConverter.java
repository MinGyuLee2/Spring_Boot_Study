package com.example.demo.domain.mission.converter;

import com.example.demo.domain.mission.dto.AvailableMissionResponse;
import com.example.demo.domain.mission.dto.MissionResponse;
import com.example.demo.domain.mission.entity.Mission;
import org.springframework.stereotype.Component;

@Component
public class MissionConverter {

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
