package com.example.demo.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * email/password 기반 로그인 요청 DTO입니다.
 */
public record MemberLoginRequest(
        @NotBlank
        @Email
        String email,

        @NotBlank
        String password
) {
}
