package com.example.demo.domain.member.dto;

import com.example.demo.domain.member.enums.MemberGender;
import com.example.demo.domain.member.enums.MemberStatus;

/**
 * 회원 기본 정보 조회 응답 DTO입니다.
 */
public record MemberResponse(
        Long memberId,
        String nickname,
        MemberGender gender,
        Integer totalPoint,
        MemberStatus status
) {
}
