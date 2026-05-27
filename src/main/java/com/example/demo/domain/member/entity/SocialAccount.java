package com.example.demo.domain.member.entity;

import com.example.demo.domain.member.enums.SocialProvider;
import com.example.demo.global.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "social_account",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_social_account_provider_user",
                        columnNames = {"provider", "provider_user_id"}
                )
        }
)
public class SocialAccount extends BaseTimeEntity {

    // 소셜 계정 테이블의 기본 키입니다.
    @Id
    @Column(name = "social_account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 소셜 계정이 연결된 회원입니다.
    @ManyToOne(optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // 어떤 소셜 로그인 제공자인지 저장합니다.
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private SocialProvider provider;

    // 소셜 제공자가 내려주는 사용자 고유 ID입니다.
    @Column(name = "provider_user_id", nullable = false, length = 100)
    private String providerUserId;

    // 소셜 계정에서 제공받은 이메일입니다.
    @Column(length = 100)
    private String email;

    // 소셜 계정 프로필 이미지 URL입니다.
    @Column(name = "profile_image_url", length = 255)
    private String profileImageUrl;

    public static SocialAccount create(
            Member member,
            SocialProvider provider,
            String providerUserId,
            String email,
            String profileImageUrl
    ) {
        SocialAccount socialAccount = new SocialAccount();
        socialAccount.member = member;
        socialAccount.provider = provider;
        socialAccount.providerUserId = providerUserId;
        socialAccount.email = email;
        socialAccount.profileImageUrl = profileImageUrl;
        return socialAccount;
    }
}
