package com.example.demo.domain.member.dto;

/**
 * 회원이 완료한 미션 개수 조회 응답 DTO입니다.
 */
public record MemberCompletedMissionCountResponse(
        Long memberId,
        long completedMissionCount
) {
}
