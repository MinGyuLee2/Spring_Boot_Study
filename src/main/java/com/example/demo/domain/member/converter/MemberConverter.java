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

    /**
     * 회원 엔티티를 기본 회원 조회 응답으로 변환합니다.
     */
    public MemberResponse toResponse(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getNickname(),
                member.getGender(),
                member.getTotalPoint(),
                member.getStatus()
        );
    }

    /**
     * 회원가입 결과 응답을 만듭니다.
     *
     * <p>회원 선호 음식은 연결 엔티티를 거쳐 음식 카테고리 ID 목록으로 변환합니다.</p>
     */
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

    /**
     * 회원 포인트 조회 응답으로 변환합니다.
     */
    public MemberPointResponse toPointResponse(Member member) {
        return new MemberPointResponse(member.getId(), member.getTotalPoint());
    }

    /**
     * 완료한 미션 개수 조회 응답으로 변환합니다.
     */
    public MemberCompletedMissionCountResponse toCompletedMissionCountResponse(Long memberId, long count) {
        return new MemberCompletedMissionCountResponse(memberId, count);
    }
}
