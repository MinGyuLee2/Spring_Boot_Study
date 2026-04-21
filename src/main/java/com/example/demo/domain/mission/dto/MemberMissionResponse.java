package com.example.demo.domain.mission.dto;

import com.example.demo.domain.mission.enums.MemberMissionStatus;
import java.time.LocalDateTime;

/**
 * 회원 미션 목록의 개별 항목 응답 DTO입니다.
 */
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
