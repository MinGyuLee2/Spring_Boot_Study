package com.example.demo.domain.member.enums;

/**
 * 회원 계정 상태를 표현하는 enum입니다.
 */
public enum MemberStatus {
    // 정상 이용 중인 회원입니다.
    ACTIVE,

    // 일시적으로 비활성화된 회원입니다.
    INACTIVE,

    // 탈퇴 처리된 회원입니다.
    WITHDRAWN
}
