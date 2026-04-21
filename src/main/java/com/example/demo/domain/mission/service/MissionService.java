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

    // 미션 조회용 Repository입니다.
    private final MissionRepository missionRepository;

    // Mission 엔티티를 응답 DTO로 변환합니다.
    private final MissionConverter missionConverter;

    // 미션 조회 전 회원 존재 여부를 확인합니다.
    private final MemberRepository memberRepository;

    // 미션 조회 전 지역 존재 여부를 확인합니다.
    private final RegionRepository regionRepository;

    /**
     * 미션 단건 정보를 조회합니다.
     */
    public MissionResponse getMission(Long missionId) {
        return missionRepository.findById(missionId)
                .map(missionConverter::toResponse)
                .orElseThrow(() -> new MissionException(MissionErrorCode.MISSION_NOT_FOUND));
    }

    /**
     * 특정 지역에서 회원이 아직 참여하지 않은 활성 미션 목록을 페이징 조회합니다.
     */
    public AvailableMissionPageResponse getAvailableMissions(Long memberId, Long regionId, int page, int size) {
        validateMemberAndRegion(memberId, regionId);

        // PageRequest는 페이지 번호, 페이지 크기, 정렬 조건을 함께 담는 Spring Data 객체입니다.
        Page<Mission> missions = missionRepository.findAvailableMissions(
                memberId,
                regionId,
                MissionStatus.ACTIVE,
                PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"))
        );

        // Page 안의 Mission 엔티티들을 API 응답 DTO 목록으로 변환합니다.
        List<AvailableMissionResponse> missionResponses = missions.stream()
                .map(missionConverter::toAvailableMissionResponse)
                .toList();

        // 목록 데이터와 페이지 메타데이터를 함께 내려줍니다.
        return new AvailableMissionPageResponse(
                missionResponses,
                missions.getNumber(),
                missions.getSize(),
                missions.getTotalElements(),
                missions.getTotalPages()
        );
    }

    /**
     * 사용자가 요청한 회원과 지역이 실제로 존재하는지 확인합니다.
     */
    private void validateMemberAndRegion(Long memberId, Long regionId) {
        if (!memberRepository.existsById(memberId)) {
            throw new MemberException(MemberErrorCode.MEMBER_NOT_FOUND);
        }

        if (!regionRepository.existsById(regionId)) {
            throw new RegionException(RegionErrorCode.REGION_NOT_FOUND);
        }
    }
}
