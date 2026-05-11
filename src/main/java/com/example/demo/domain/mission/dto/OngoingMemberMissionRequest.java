package com.example.demo.domain.mission.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * 현재 회원이 진행 중인 미션 목록 조회 요청 DTO입니다.
 */
public record OngoingMemberMissionRequest(
        @NotNull
        @Positive
        Long memberId,

        @NotNull
        @Min(0)
        Integer page,

        @NotNull
        @Min(1)
        @Max(50)
        Integer size
) {
}
