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

    private final RegionRepository regionRepository;
    private final RegionConverter regionConverter;

    public RegionResponse getRegion(Long regionId) {
        return regionRepository.findById(regionId)
                .map(regionConverter::toResponse)
                .orElseThrow(() -> new RegionException(RegionErrorCode.REGION_NOT_FOUND));
    }
}
