package com.example.demo.domain.member.dto;

import com.example.demo.domain.member.enums.MemberGender;
import com.example.demo.domain.member.enums.MemberStatus;

public record MemberResponse(
        Long memberId,
        String nickname,
        MemberGender gender,
        Integer totalPoint,
        MemberStatus status
) {
}
