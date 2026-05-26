package com.example.demo.domain.member.service;

import com.example.demo.domain.member.dto.AuthTokenResponse;
import com.example.demo.domain.member.dto.KakaoLoginRequest;
import com.example.demo.domain.member.dto.MemberLoginRequest;
import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.entity.SocialAccount;
import com.example.demo.domain.member.enums.MemberGender;
import com.example.demo.domain.member.enums.SocialProvider;
import com.example.demo.domain.member.exception.MemberErrorCode;
import com.example.demo.domain.member.exception.MemberException;
import com.example.demo.domain.member.repository.MemberRepository;
import com.example.demo.domain.member.repository.SocialAccountRepository;
import com.example.demo.global.auth.JwtTokenProvider;
import com.example.demo.global.auth.oauth.KakaoOAuthClient;
import com.example.demo.global.auth.oauth.KakaoUserInfo;
import java.time.LocalDate;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private static final int NICKNAME_MAX_LENGTH = 20;
    private static final int EMAIL_MAX_LENGTH = 100;
    private static final int PROFILE_IMAGE_MAX_LENGTH = 255;
    private static final String OAUTH_DEFAULT_ADDRESS = "카카오 로그인 회원";

    private final MemberRepository memberRepository;
    private final SocialAccountRepository socialAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final KakaoOAuthClient kakaoOAuthClient;

    public AuthTokenResponse login(MemberLoginRequest request) {
        Member member = memberRepository.findByEmail(request.email())
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_LOGIN_FAILED));

        if (!passwordEncoder.matches(request.password(), member.getPassword())) {
            throw new MemberException(MemberErrorCode.MEMBER_LOGIN_FAILED);
        }

        return jwtTokenProvider.generateAccessToken(member);
    }

    @Transactional
    public AuthTokenResponse loginWithKakao(KakaoLoginRequest request) {
        KakaoUserInfo kakaoUserInfo = kakaoOAuthClient.getUserInfo(request.code(), request.redirectUri());

        Member member = socialAccountRepository.findByProviderAndProviderUserId(
                        SocialProvider.KAKAO,
                        kakaoUserInfo.providerUserId()
                )
                .map(SocialAccount::getMember)
                .orElseGet(() -> createOrConnectKakaoMember(kakaoUserInfo));

        return jwtTokenProvider.generateAccessToken(member);
    }

    private Member createOrConnectKakaoMember(KakaoUserInfo kakaoUserInfo) {
        String email = normalizeKakaoEmail(kakaoUserInfo);
        Member member = memberRepository.findByEmail(email)
                .orElseGet(() -> memberRepository.save(Member.create(
                        email,
                        passwordEncoder.encode(UUID.randomUUID().toString()),
                        truncate(defaultIfBlank(kakaoUserInfo.nickname(), "kakao" + kakaoUserInfo.providerUserId()),
                                NICKNAME_MAX_LENGTH),
                        MemberGender.NONE,
                        LocalDate.of(2000, 1, 1),
                        OAUTH_DEFAULT_ADDRESS
                )));

        SocialAccount socialAccount = SocialAccount.create(
                member,
                SocialProvider.KAKAO,
                kakaoUserInfo.providerUserId(),
                truncate(kakaoUserInfo.email(), EMAIL_MAX_LENGTH),
                truncate(kakaoUserInfo.profileImageUrl(), PROFILE_IMAGE_MAX_LENGTH)
        );
        socialAccountRepository.save(socialAccount);

        return member;
    }

    private String normalizeKakaoEmail(KakaoUserInfo kakaoUserInfo) {
        String email = defaultIfBlank(kakaoUserInfo.email(), "kakao_" + kakaoUserInfo.providerUserId() + "@kakao.local");
        return truncate(email, EMAIL_MAX_LENGTH);
    }

    private String defaultIfBlank(String value, String defaultValue) {
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return value;
    }

    private String truncate(String value, int maxLength) {
        if (value == null || value.length() <= maxLength) {
            return value;
        }
        return value.substring(0, maxLength);
    }
}
