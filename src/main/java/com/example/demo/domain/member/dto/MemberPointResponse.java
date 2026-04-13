package com.example.demo.domain.member.dto;

public record MemberPointResponse(
        Long memberId,
        Integer totalPoint
) {
}
