package com.example.demo.domain.mission.service;

import com.example.demo.domain.member.exception.MemberErrorCode;
import com.example.demo.domain.member.exception.MemberException;
import com.example.demo.domain.member.repository.MemberRepository;
import com.example.demo.domain.mission.converter.MemberMissionConverter;
import com.example.demo.domain.mission.dto.MemberMissionCompleteResponse;
import com.example.demo.domain.mission.dto.MemberMissionPageResponse;
import com.example.demo.domain.mission.dto.MemberMissionResponse;
import com.example.demo.domain.mission.entity.MemberMission;
import com.example.demo.domain.mission.enums.MemberMissionStatus;
import com.example.demo.domain.mission.exception.MemberMissionErrorCode;
import com.example.demo.domain.mission.exception.MemberMissionException;
import com.example.demo.domain.mission.repository.MemberMissionRepository;
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
public class MemberMissionService {

    private final MemberMissionRepository memberMissionRepository;
    private final MemberMissionConverter memberMissionConverter;
    private final MemberRepository memberRepository;

    public MemberMissionPageResponse getMemberMissions(
            Long memberId,
            MemberMissionStatus status,
            int page,
            int size
    ) {
        validateMember(memberId);

        Page<MemberMission> memberMissions = memberMissionRepository.findByMember_IdAndStatus(
                memberId,
                status,
                PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"))
        );

        List<MemberMissionResponse> responses = memberMissions.stream()
                .map(memberMissionConverter::toResponse)
                .toList();

        return new MemberMissionPageResponse(
                responses,
                memberMissions.getNumber(),
                memberMissions.getSize(),
                memberMissions.getTotalElements(),
                memberMissions.getTotalPages()
        );
    }

    @Transactional
    public MemberMissionCompleteResponse completeMission(Long memberId, Long memberMissionId) {
        validateMember(memberId);

        MemberMission memberMission = memberMissionRepository.findByIdAndMember_Id(memberMissionId, memberId)
                .orElseThrow(() -> new MemberMissionException(MemberMissionErrorCode.MEMBER_MISSION_NOT_FOUND));
        memberMission.complete();
        return memberMissionConverter.toCompleteResponse(memberMission);
    }

    private void validateMember(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new MemberException(MemberErrorCode.MEMBER_NOT_FOUND);
        }
    }
}
