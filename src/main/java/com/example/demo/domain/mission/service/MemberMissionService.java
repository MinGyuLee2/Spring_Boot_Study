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

    // 회원 미션 엔티티를 조회하고 저장하는 Repository입니다.
    private final MemberMissionRepository memberMissionRepository;

    // MemberMission 엔티티를 응답 DTO로 변환합니다.
    private final MemberMissionConverter memberMissionConverter;

    // 회원 존재 여부를 확인하기 위해 사용합니다.
    private final MemberRepository memberRepository;

    /**
     * 회원의 미션 목록을 상태별로 페이징 조회합니다.
     */
    public MemberMissionPageResponse getMemberMissions(
            Long memberId,
            MemberMissionStatus status,
            int page,
            int size
    ) {
        validateMember(memberId);

        // 회원 ID와 미션 상태로 필터링하고, ID 오름차순으로 페이지를 가져옵니다.
        Page<MemberMission> memberMissions = memberMissionRepository.findByMember_IdAndStatus(
                memberId,
                status,
                PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"))
        );

        // Page 안의 엔티티를 응답 DTO 목록으로 변환합니다.
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

    /**
     * 회원 미션을 완료 상태로 변경합니다.
     *
     * <p>상태 변경이 일어나므로 readOnly가 아닌 쓰기 트랜잭션으로 실행합니다.</p>
     */
    @Transactional
    public MemberMissionCompleteResponse completeMission(Long memberId, Long memberMissionId) {
        validateMember(memberId);

        // 본인의 회원 미션만 완료할 수 있도록 memberId 조건을 함께 사용합니다.
        MemberMission memberMission = memberMissionRepository.findByIdAndMember_Id(memberMissionId, memberId)
                .orElseThrow(() -> new MemberMissionException(MemberMissionErrorCode.MEMBER_MISSION_NOT_FOUND));

        if (memberMission.getStatus() != MemberMissionStatus.CHALLENGING) {
            throw new MemberMissionException(MemberMissionErrorCode.MEMBER_MISSION_NOT_CHALLENGING);
        }

        memberMission.complete();
        memberMission.getMember().addPoint(memberMission.getMission().getRewardPoint());
        return memberMissionConverter.toCompleteResponse(memberMission);
    }

    /**
     * 회원 존재 여부를 확인합니다.
     */
    private void validateMember(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new MemberException(MemberErrorCode.MEMBER_NOT_FOUND);
        }
    }
}
