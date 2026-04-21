package com.example.demo.domain.region.service;

import com.example.demo.domain.region.converter.RegionConverter;
import com.example.demo.domain.region.dto.RegionResponse;
import com.example.demo.domain.region.exception.RegionErrorCode;
import com.example.demo.domain.region.exception.RegionException;
import com.example.demo.domain.region.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RegionService {

    // 지역 엔티티를 조회하는 Repository입니다.
    private final RegionRepository regionRepository;

    // Region 엔티티를 응답 DTO로 변환합니다.
    private final RegionConverter regionConverter;

    /**
     * 지역 ID로 단건 조회합니다.
     */
    public RegionResponse getRegion(Long regionId) {
        return regionRepository.findById(regionId)
                .map(regionConverter::toResponse)
                .orElseThrow(() -> new RegionException(RegionErrorCode.REGION_NOT_FOUND));
    }
}
