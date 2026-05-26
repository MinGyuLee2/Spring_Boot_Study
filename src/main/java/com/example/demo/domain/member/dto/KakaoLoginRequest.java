package com.example.demo.domain.member.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * 카카오 OAuth 인가 코드 로그인 요청 DTO입니다.
 */
public record KakaoLoginRequest(
        @NotBlank
        String code,

        String redirectUri
) {
}
