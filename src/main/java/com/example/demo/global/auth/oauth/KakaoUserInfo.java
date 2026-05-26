package com.example.demo.global.auth.oauth;

/**
 * 카카오 사용자 정보 API에서 서비스 가입에 필요한 값만 추린 DTO입니다.
 */
public record KakaoUserInfo(
        String providerUserId,
        String email,
        String nickname,
        String profileImageUrl
) {
}
