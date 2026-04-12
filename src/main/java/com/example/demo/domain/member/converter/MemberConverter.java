package com.example.demo.domain.member.converter;

import com.example.demo.domain.member.entity.MemberFoodPreference;
import com.example.demo.domain.member.dto.MemberCompletedMissionCountResponse;
import com.example.demo.domain.member.dto.MemberPointResponse;
import com.example.demo.domain.member.dto.MemberResponse;
import com.example.demo.domain.member.dto.MemberSignUpResponse;
import java.util.List;
import com.example.demo.domain.member.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberConverter {

    public MemberResponse toResponse(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getNickname(),
                member.getGender(),
                member.getTotalPoint(),
                member.getStatus()
        );
    }

    public MemberSignUpResponse toSignUpResponse(Member member) {
        List<Long> foodCategoryIds = member.getFoodPreferences().stream()
                .map(MemberFoodPreference::getFoodCategory)
                .map(foodCategory -> foodCategory.getId())
                .toList();

        return new MemberSignUpResponse(
                member.getId(),
                member.getEmail(),
                member.getNickname(),
                member.getGender(),
                member.getBirthDate(),
                member.getAddress(),
                foodCategoryIds,
                member.getStatus(),
                member.getTotalPoint()
        );
    }

    public MemberPointResponse toPointResponse(Member member) {
        return new MemberPointResponse(member.getId(), member.getTotalPoint());
    }

    public MemberCompletedMissionCountResponse toCompletedMissionCountResponse(Long memberId, long count) {
        return new MemberCompletedMissionCountResponse(memberId, count);
    }
}
