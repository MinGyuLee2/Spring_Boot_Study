package com.example.demo.domain.region.controller;

import com.example.demo.domain.region.dto.RegionResponse;
import com.example.demo.domain.region.service.RegionService;
import com.example.demo.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/regions")
public class RegionController {

    // 지역 조회 로직은 Service 계층에 위임합니다.
    private final RegionService regionService;

    /**
     * 지역 단건 정보를 조회합니다.
     */
    @GetMapping("/{regionId}")
    public ApiResponse<RegionResponse> getRegion(@PathVariable Long regionId) {
        return ApiResponse.onSuccess(regionService.getRegion(regionId));
    }
}
