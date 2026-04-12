package com.example.demo.domain.region.converter;

import com.example.demo.domain.region.dto.RegionResponse;
import com.example.demo.domain.region.entity.Region;
import org.springframework.stereotype.Component;

@Component
public class RegionConverter {

    public RegionResponse toResponse(Region region) {
        return new RegionResponse(
                region.getId(),
                region.getName()
        );
    }
}
