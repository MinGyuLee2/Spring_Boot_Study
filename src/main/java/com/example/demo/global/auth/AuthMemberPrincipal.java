package com.example.demo.global.auth;

/**
 * JWT 인증이 완료된 현재 회원 정보입니다.
 */
public record AuthMemberPrincipal(
        Long memberId,
        String email,
        String nickname
) {
}
