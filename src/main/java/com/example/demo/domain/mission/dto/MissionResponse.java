package com.example.demo.domain.mission.dto;

import com.example.demo.domain.mission.enums.MissionStatus;

/**
 * 미션 단건 조회 응답 DTO입니다.
 */
public record MissionResponse(
        Long missionId,
        Long storeId,
        String title,
        String description,
        Integer rewardPoint,
        MissionStatus status
) {
}
