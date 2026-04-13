package com.example.demo.domain.member.dto;

public record MemberCompletedMissionCountResponse(
        Long memberId,
        long completedMissionCount
) {
}
