package com.example.demo.domain.member.dto;

import com.example.demo.domain.member.enums.MemberGender;
import com.example.demo.domain.member.enums.MemberStatus;
import java.time.LocalDate;
import java.util.List;

/**
 * 회원가입 성공 후 클라이언트에게 반환하는 응답 DTO입니다.
 */
public record MemberSignUpResponse(
        Long memberId,
        String email,
        String nickname,
        MemberGender gender,
        LocalDate birthDate,
        String address,
        List<Long> foodCategoryIds,
        MemberStatus status,
        Integer totalPoint
) {
}
