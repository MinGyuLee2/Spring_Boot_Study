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

    private final MemberMissionService memberMissionService;

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

    @PatchMapping(value = "/{memberMissionId}/complete", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<MemberMissionCompleteResponse> completeMission(
            @RequestHeader(value = "X-Member-Id", defaultValue = "1") Long memberId,
            @PathVariable Long memberMissionId
    ) {
        return ApiResponse.onSuccess(memberMissionService.completeMission(memberId, memberMissionId));
    }

    private void validateMemberMissionRequest(MemberMissionStatus status, Integer page, Integer size) {
        if (status == null || page == null || size == null || page < 0 || size < 1) {
            throw new DomainException(GeneralErrorCode.BAD_REQUEST);
        }
    }
}
