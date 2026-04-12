package com.example.demo.domain.mission.dto;

import com.example.demo.domain.mission.enums.MissionStatus;

public record MissionResponse(
        Long missionId,
        Long storeId,
        String title,
        String description,
        Integer rewardPoint,
        MissionStatus status
) {
}
