package com.example.demo.domain.member.exception;

import com.example.demo.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 회원 도메인에서 사용할 에러 코드 목록입니다.
 *
 * <p>각 항목은 HTTP 상태, 애플리케이션 에러 코드, 클라이언트 메시지를 함께 가집니다.</p>
 */
@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements BaseErrorCode {
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER404", "회원을 찾을 수 없습니다."),
    MEMBER_EMAIL_DUPLICATED(HttpStatus.CONFLICT, "MEMBER409", "이미 존재하는 이메일입니다."),
    MEMBER_LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "MEMBER401", "이메일 또는 비밀번호가 올바르지 않습니다."),
    KAKAO_OAUTH_FAILED(HttpStatus.BAD_REQUEST, "KAKAO400", "카카오 로그인에 실패했습니다.");

    // GlobalExceptionHandler가 이 값들을 읽어 실패 응답을 만듭니다.
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
