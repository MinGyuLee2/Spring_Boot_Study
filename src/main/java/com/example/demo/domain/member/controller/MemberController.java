package com.example.demo.domain.member.controller;

import com.example.demo.domain.member.dto.MemberCompletedMissionCountResponse;
import com.example.demo.domain.member.dto.MemberMyPageResponse;
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

    // 회원 관련 비즈니스 로직은 Service 계층에 위임합니다.
    private final MemberService memberService;

    /**
     * 회원가입 API입니다.
     *
     * <p>{@code @Valid}가 붙어 있어서 MemberSignUpRequest의 검증 어노테이션을
     * 통과하지 못하면 컨트롤러 메서드 실행 전에 400 응답으로 처리됩니다.</p>
     */
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<MemberSignUpResponse>> signUp(@Valid @RequestBody MemberSignUpRequest request) {
        MemberSignUpResponse response = memberService.signUp(request);
        return ResponseEntity.status(GeneralSuccessCode.CREATED.getHttpStatus())
                .body(ApiResponse.onSuccess(GeneralSuccessCode.CREATED, response));
    }

    /**
     * 마이페이지 화면에 필요한 현재 회원 요약 정보를 조회합니다.
     *
     * <p>아직 인증 기능이 없기 때문에 임시로 X-Member-Id 헤더를 사용합니다.</p>
     */
    @GetMapping("/me")
    public ApiResponse<MemberMyPageResponse> getMyPage(
            @RequestHeader(value = "X-Member-Id", defaultValue = "1") Long memberId
    ) {
        return ApiResponse.onSuccess(memberService.getMyPage(memberId));
    }

    /**
     * 현재 회원의 포인트를 조회합니다.
     *
     * <p>아직 인증 기능이 없기 때문에 임시로 X-Member-Id 헤더를 사용합니다.</p>
     */
    @GetMapping("/me/points")
    public ApiResponse<MemberPointResponse> getMyPoints(
            @RequestHeader(value = "X-Member-Id", defaultValue = "1") Long memberId
    ) {
        return ApiResponse.onSuccess(memberService.getMyPoints(memberId));
    }

    /**
     * 현재 회원이 완료한 미션 개수를 조회합니다.
     */
    @GetMapping("/me/missions/completed/count")
    public ApiResponse<MemberCompletedMissionCountResponse> getCompletedMissionCount(
            @RequestHeader(value = "X-Member-Id", defaultValue = "1") Long memberId
    ) {
        return ApiResponse.onSuccess(memberService.getCompletedMissionCount(memberId));
    }
}
