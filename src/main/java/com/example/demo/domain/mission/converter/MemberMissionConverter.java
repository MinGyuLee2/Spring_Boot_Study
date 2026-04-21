package com.example.demo.domain.mission.converter;

import com.example.demo.domain.mission.dto.MemberMissionCompleteResponse;
import com.example.demo.domain.mission.dto.MemberMissionResponse;
import com.example.demo.domain.mission.entity.MemberMission;
import org.springframework.stereotype.Component;

@Component
public class MemberMissionConverter {

    /**
     * 회원 미션 엔티티를 목록 조회 응답으로 변환합니다.
     */
    public MemberMissionResponse toResponse(MemberMission memberMission) {
        return new MemberMissionResponse(
                memberMission.getId(),
                memberMission.getMission().getId(),
                memberMission.getMission().getTitle(),
                memberMission.getMission().getRewardPoint(),
                memberMission.getMission().getStore().getId(),
                memberMission.getMission().getStore().getName(),
                memberMission.getStatus(),
                memberMission.getStartedAt(),
                memberMission.getCompletedAt()
        );
    }

    /**
     * 미션 완료 처리 후 필요한 상태와 완료 시간만 응답으로 변환합니다.
     */
    public MemberMissionCompleteResponse toCompleteResponse(MemberMission memberMission) {
        return new MemberMissionCompleteResponse(
                memberMission.getId(),
                memberMission.getStatus(),
                memberMission.getCompletedAt()
        );
    }
}
