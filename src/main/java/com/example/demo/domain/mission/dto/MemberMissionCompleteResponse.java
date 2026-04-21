package com.example.demo.domain.mission.dto;

import com.example.demo.domain.mission.enums.MemberMissionStatus;
import java.time.LocalDateTime;

/**
 * 회원 미션 완료 처리 결과 응답 DTO입니다.
 */
public record MemberMissionCompleteResponse(
        Long memberMissionId,
        MemberMissionStatus status,
        LocalDateTime completedAt
) {
}
