package com.example.demo.domain.member.controller;

import com.example.demo.domain.member.dto.MemberCompletedMissionCountResponse;
import com.example.demo.domain.member.dto.MemberPointResponse;
import com.example.demo.domain.member.dto.MemberSignUpRequest;
import com.example.demo.domain.member.dto.MemberSignUpResponse;
import com.example.demo.domain.member.service.MemberService;
import com.example.demo.global.apiPayload.ApiResponse;
import com.example.demo.global.apiPayload.code.GeneralSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<MemberSignUpResponse>> signUp(@Valid @RequestBody MemberSignUpRequest request) {
        MemberSignUpResponse response = memberService.signUp(request);
        return ResponseEntity.status(GeneralSuccessCode.CREATED.getHttpStatus())
                .body(ApiResponse.onSuccess(GeneralSuccessCode.CREATED, response));
    }

    @GetMapping("/me/points")
    public ApiResponse<MemberPointResponse> getMyPoints(
            @RequestHeader(value = "X-Member-Id", defaultValue = "1") Long memberId
    ) {
        return ApiResponse.onSuccess(memberService.getMyPoints(memberId));
    }

    @GetMapping("/me/missions/completed/count")
    public ApiResponse<MemberCompletedMissionCountResponse> getCompletedMissionCount(
            @RequestHeader(value = "X-Member-Id", defaultValue = "1") Long memberId
    ) {
        return ApiResponse.onSuccess(memberService.getCompletedMissionCount(memberId));
    }
}
