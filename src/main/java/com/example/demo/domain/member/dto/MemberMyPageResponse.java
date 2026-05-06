package com.example.demo.domain.member.dto;

/**
 * 마이페이지 화면에 필요한 회원 요약 정보 응답 DTO입니다.
 */
public record MemberMyPageResponse(
        Long memberId,
        String nickname,
        String email,
        Integer totalPoint,
        long writtenReviewCount,
        long completedMissionCount
) {
}
