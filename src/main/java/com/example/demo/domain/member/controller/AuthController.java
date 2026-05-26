package com.example.demo.domain.member.controller;

import com.example.demo.domain.member.dto.AuthTokenResponse;
import com.example.demo.domain.member.dto.KakaoLoginRequest;
import com.example.demo.domain.member.dto.MemberLoginRequest;
import com.example.demo.domain.member.service.AuthService;
import com.example.demo.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "JWT 로그인")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "로그인 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "로그인 실패")
    })
    @PostMapping("/login")
    public ApiResponse<AuthTokenResponse> login(@Valid @RequestBody MemberLoginRequest request) {
        return ApiResponse.onSuccess(authService.login(request));
    }

    @Operation(summary = "카카오 OAuth 로그인")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "카카오 로그인 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "카카오 로그인 실패")
    })
    @PostMapping("/oauth/kakao")
    public ApiResponse<AuthTokenResponse> kakaoLogin(@Valid @RequestBody KakaoLoginRequest request) {
        return ApiResponse.onSuccess(authService.loginWithKakao(request));
    }
}
