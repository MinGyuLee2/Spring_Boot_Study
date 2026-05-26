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
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER404", "Member not found"),
    MEMBER_EMAIL_DUPLICATED(HttpStatus.CONFLICT, "MEMBER409", "Email already exists"),
    MEMBER_LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "MEMBER401", "Email or password is invalid"),
    KAKAO_OAUTH_FAILED(HttpStatus.BAD_REQUEST, "KAKAO400", "Kakao OAuth login failed");

    // GlobalExceptionHandler가 이 값들을 읽어 실패 응답을 만듭니다.
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
