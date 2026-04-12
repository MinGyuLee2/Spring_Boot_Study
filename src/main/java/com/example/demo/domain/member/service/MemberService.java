package com.example.demo.domain.member.service;

import com.example.demo.domain.food.entity.FoodCategory;
import com.example.demo.domain.food.exception.FoodCategoryErrorCode;
import com.example.demo.domain.food.exception.FoodCategoryException;
import com.example.demo.domain.food.repository.FoodCategoryRepository;
import com.example.demo.domain.member.converter.MemberConverter;
import com.example.demo.domain.member.dto.MemberCompletedMissionCountResponse;
import com.example.demo.domain.member.dto.MemberPointResponse;
import com.example.demo.domain.member.dto.MemberResponse;
import com.example.demo.domain.member.dto.MemberSignUpRequest;
import com.example.demo.domain.member.dto.MemberSignUpResponse;
import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.entity.MemberFoodPreference;
import com.example.demo.domain.member.exception.MemberErrorCode;
import com.example.demo.domain.member.exception.MemberException;
import com.example.demo.domain.member.repository.MemberFoodPreferenceRepository;
import com.example.demo.domain.member.repository.MemberRepository;
import com.example.demo.domain.mission.enums.MemberMissionStatus;
import com.example.demo.domain.mission.repository.MemberMissionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final FoodCategoryRepository foodCategoryRepository;
    private final MemberRepository memberRepository;
    private final MemberFoodPreferenceRepository memberFoodPreferenceRepository;
    private final MemberMissionRepository memberMissionRepository;
    private final MemberConverter memberConverter;

    @Transactional
    public MemberSignUpResponse signUp(MemberSignUpRequest request) {
        Member member = Member.create(
                request.email(),
                request.password(),
                request.nickname(),
                request.gender(),
                request.birthDate(),
                request.address()
        );
        Member savedMember = memberRepository.save(member);

        List<FoodCategory> foodCategories = foodCategoryRepository.findAllById(request.foodCategoryIds());
        if (foodCategories.size() != request.foodCategoryIds().size()) {
            throw new FoodCategoryException(FoodCategoryErrorCode.FOOD_CATEGORY_NOT_FOUND);
        }

        List<MemberFoodPreference> preferences = foodCategories.stream()
                .map(foodCategory -> MemberFoodPreference.create(savedMember, foodCategory))
                .toList();
        memberFoodPreferenceRepository.saveAll(preferences);
        savedMember.getFoodPreferences().addAll(preferences);

        return memberConverter.toSignUpResponse(savedMember);
    }

    public MemberResponse getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .map(memberConverter::toResponse)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
    }

    public MemberPointResponse getMyPoints(Long memberId) {
        Member member = getMemberEntity(memberId);
        return memberConverter.toPointResponse(member);
    }

    public MemberCompletedMissionCountResponse getCompletedMissionCount(Long memberId) {
        getMemberEntity(memberId);
        long count = memberMissionRepository.countByMember_IdAndStatus(memberId, MemberMissionStatus.COMPLETED);
        return memberConverter.toCompletedMissionCountResponse(memberId, count);
    }

    private Member getMemberEntity(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
    }
}
