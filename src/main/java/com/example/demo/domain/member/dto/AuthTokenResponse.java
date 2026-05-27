package com.example.demo.domain.member.dto;

/**
 * 로그인 성공 후 발급하는 JWT 토큰 응답입니다.
 */
public record AuthTokenResponse(
        String tokenType,
        String accessToken,
        Long expiresIn,
        Long memberId,
        String email,
        String nickname
) {
}
