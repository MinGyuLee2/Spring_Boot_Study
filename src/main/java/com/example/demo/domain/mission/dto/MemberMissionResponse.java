package com.example.demo.domain.mission.dto;

import com.example.demo.domain.mission.enums.MemberMissionStatus;
import java.time.LocalDateTime;

public record MemberMissionResponse(
        Long memberMissionId,
        Long missionId,
        String title,
        Integer rewardPoint,
        Long storeId,
        String storeName,
        MemberMissionStatus status,
        LocalDateTime startedAt,
        LocalDateTime completedAt
) {
}
