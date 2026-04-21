package com.example.demo.domain.mission.controller;

import com.example.demo.domain.mission.dto.MemberMissionCompleteResponse;
import com.example.demo.domain.mission.dto.MemberMissionPageResponse;
import com.example.demo.domain.mission.enums.MemberMissionStatus;
import com.example.demo.domain.mission.service.MemberMissionService;
import com.example.demo.global.apiPayload.ApiResponse;
import com.example.demo.global.apiPayload.code.GeneralErrorCode;
import com.example.demo.global.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member-missions")
public class MemberMissionController {

    // 회원 미션 목록 조회와 완료 처리는 Service 계층에 위임합니다.
    private final MemberMissionService memberMissionService;

    /**
     * 현재 회원의 미션 목록을 상태별로 조회합니다.
     *
     * <p>status는 CHALLENGING, COMPLETED, FAILED 중 하나로 전달됩니다.</p>
     */
    @GetMapping
    public ApiResponse<MemberMissionPageResponse> getMemberMissions(
            @RequestHeader(value = "X-Member-Id", defaultValue = "1") Long memberId,
            @RequestParam(required = false) MemberMissionStatus status,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        validateMemberMissionRequest(status, page, size);

        return ApiResponse.onSuccess(memberMissionService.getMemberMissions(memberId, status, page, size));
    }

    /**
     * 회원이 도전 중인 미션을 완료 처리합니다.
     */
    @PatchMapping(value = "/{memberMissionId}/complete", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<MemberMissionCompleteResponse> completeMission(
            @RequestHeader(value = "X-Member-Id", defaultValue = "1") Long memberId,
            @PathVariable Long memberMissionId
    ) {
        return ApiResponse.onSuccess(memberMissionService.completeMission(memberId, memberMissionId));
    }

    /**
     * 상태 필터와 페이징 값이 올바른지 검증합니다.
     */
    private void validateMemberMissionRequest(MemberMissionStatus status, Integer page, Integer size) {
        if (status == null || page == null || size == null || page < 0 || size < 1) {
            throw new DomainException(GeneralErrorCode.BAD_REQUEST);
        }
    }
}
