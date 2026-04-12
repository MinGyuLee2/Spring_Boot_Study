package com.example.demo.domain.mission.converter;

import com.example.demo.domain.mission.dto.MemberMissionCompleteResponse;
import com.example.demo.domain.mission.dto.MemberMissionResponse;
import com.example.demo.domain.mission.entity.MemberMission;
import org.springframework.stereotype.Component;

@Component
public class MemberMissionConverter {

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

    public MemberMissionCompleteResponse toCompleteResponse(MemberMission memberMission) {
        return new MemberMissionCompleteResponse(
                memberMission.getId(),
                memberMission.getStatus(),
                memberMission.getCompletedAt()
        );
    }
}
