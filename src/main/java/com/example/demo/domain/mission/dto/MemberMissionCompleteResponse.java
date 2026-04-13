package com.example.demo.domain.mission.dto;

import com.example.demo.domain.mission.enums.MemberMissionStatus;
import java.time.LocalDateTime;

public record MemberMissionCompleteResponse(
        Long memberMissionId,
        MemberMissionStatus status,
        LocalDateTime completedAt
) {
}
