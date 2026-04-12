package com.example.demo.domain.mission.controller;

import com.example.demo.domain.mission.dto.AvailableMissionPageResponse;
import com.example.demo.domain.mission.service.MissionService;
import com.example.demo.global.apiPayload.ApiResponse;
import com.example.demo.global.apiPayload.code.GeneralErrorCode;
import com.example.demo.global.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/missions")
public class MissionController {

    private final MissionService missionService;

    @GetMapping("/available")
    public ApiResponse<AvailableMissionPageResponse> getAvailableMissions(
            @RequestHeader(value = "X-Member-Id", defaultValue = "1") Long memberId,
            @RequestParam(required = false) Long regionId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        validateAvailableMissionRequest(regionId, page, size);

        return ApiResponse.onSuccess(missionService.getAvailableMissions(memberId, regionId, page, size));
    }

    private void validateAvailableMissionRequest(Long regionId, Integer page, Integer size) {
        if (regionId == null || page == null || size == null || page < 0 || size < 1) {
            throw new DomainException(GeneralErrorCode.BAD_REQUEST);
        }
    }
}
