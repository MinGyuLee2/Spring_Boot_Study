package com.example.demo.domain.mission.service;

import com.example.demo.domain.member.exception.MemberErrorCode;
import com.example.demo.domain.member.exception.MemberException;
import com.example.demo.domain.member.repository.MemberRepository;
import com.example.demo.domain.mission.dto.AvailableMissionPageResponse;
import com.example.demo.domain.mission.dto.AvailableMissionResponse;
import com.example.demo.domain.mission.converter.MissionConverter;
import com.example.demo.domain.mission.dto.MissionResponse;
import com.example.demo.domain.mission.entity.Mission;
import com.example.demo.domain.mission.enums.MissionStatus;
import com.example.demo.domain.mission.exception.MissionErrorCode;
import com.example.demo.domain.mission.exception.MissionException;
import com.example.demo.domain.mission.repository.MissionRepository;
import com.example.demo.domain.region.exception.RegionErrorCode;
import com.example.demo.domain.region.exception.RegionException;
import com.example.demo.domain.region.repository.RegionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionService {

    private final MissionRepository missionRepository;
    private final MissionConverter missionConverter;
    private final MemberRepository memberRepository;
    private final RegionRepository regionRepository;

    public MissionResponse getMission(Long missionId) {
        return missionRepository.findById(missionId)
                .map(missionConverter::toResponse)
                .orElseThrow(() -> new MissionException(MissionErrorCode.MISSION_NOT_FOUND));
    }

    public AvailableMissionPageResponse getAvailableMissions(Long memberId, Long regionId, int page, int size) {
        validateMemberAndRegion(memberId, regionId);

        Page<Mission> missions = missionRepository.findAvailableMissions(
                memberId,
                regionId,
                MissionStatus.ACTIVE,
                PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"))
        );

        List<AvailableMissionResponse> missionResponses = missions.stream()
                .map(missionConverter::toAvailableMissionResponse)
                .toList();

        return new AvailableMissionPageResponse(
                missionResponses,
                missions.getNumber(),
                missions.getSize(),
                missions.getTotalElements(),
                missions.getTotalPages()
        );
    }

    private void validateMemberAndRegion(Long memberId, Long regionId) {
        if (!memberRepository.existsById(memberId)) {
            throw new MemberException(MemberErrorCode.MEMBER_NOT_FOUND);
        }

        if (!regionRepository.existsById(regionId)) {
            throw new RegionException(RegionErrorCode.REGION_NOT_FOUND);
        }
    }
}
