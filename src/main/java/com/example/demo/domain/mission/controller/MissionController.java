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

    // 미션 조회와 검증 로직은 Service 계층에 위임합니다.
    private final MissionService missionService;

    /**
     * 특정 지역에서 현재 회원이 아직 도전하지 않은 미션 목록을 조회합니다.
     *
     * <p>page는 0부터 시작하고, size는 한 페이지에 담을 데이터 개수입니다.</p>
     */
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

    /**
     * 필수 쿼리 파라미터와 페이징 값의 기본 범위를 검증합니다.
     */
    private void validateAvailableMissionRequest(Long regionId, Integer page, Integer size) {
        if (regionId == null || page == null || size == null || page < 0 || size < 1) {
            throw new DomainException(GeneralErrorCode.BAD_REQUEST);
        }
    }
}
