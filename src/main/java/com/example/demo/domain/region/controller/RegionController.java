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

    private final RegionService regionService;

    @GetMapping("/{regionId}")
    public ApiResponse<RegionResponse> getRegion(@PathVariable Long regionId) {
        return ApiResponse.onSuccess(regionService.getRegion(regionId));
    }
}
