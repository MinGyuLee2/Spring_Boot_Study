package com.example.demo.domain.member.dto;

/**
 * 회원 포인트 조회 응답 DTO입니다.
 */
public record MemberPointResponse(
        Long memberId,
        Integer totalPoint
) {
}
