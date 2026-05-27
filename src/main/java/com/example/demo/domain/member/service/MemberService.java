package com.example.demo.domain.member.service;

import com.example.demo.domain.food.entity.FoodCategory;
import com.example.demo.domain.food.exception.FoodCategoryErrorCode;
import com.example.demo.domain.food.exception.FoodCategoryException;
import com.example.demo.domain.food.repository.FoodCategoryRepository;
import com.example.demo.domain.member.converter.MemberConverter;
import com.example.demo.domain.member.dto.AuthTokenResponse;
import com.example.demo.domain.member.dto.MemberCompletedMissionCountResponse;
import com.example.demo.domain.member.dto.MemberMyPageResponse;
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
import com.example.demo.domain.review.repository.ReviewRepository;
import com.example.demo.global.auth.JwtTokenProvider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    // 회원가입 시 선택한 음식 카테고리가 실제 존재하는지 확인할 때 사용합니다.
    private final FoodCategoryRepository foodCategoryRepository;

    // 회원 엔티티를 저장하거나 조회하는 JPA Repository입니다.
    private final MemberRepository memberRepository;

    // 회원가입 시 평문 비밀번호를 BCrypt 해시로 변환합니다.
    private final PasswordEncoder passwordEncoder;

    // 회원과 음식 카테고리의 다대다 관계를 연결 테이블 엔티티로 저장합니다.
    private final MemberFoodPreferenceRepository memberFoodPreferenceRepository;

    // 회원이 완료한 미션 개수를 계산할 때 사용합니다.
    private final MemberMissionRepository memberMissionRepository;

    // 회원이 작성한 리뷰 개수를 계산할 때 사용합니다.
    private final ReviewRepository reviewRepository;

    // Entity를 API 응답 DTO로 변환하는 역할입니다.
    private final MemberConverter memberConverter;

    // 회원가입 성공 직후 로그인 상태로 만들기 위한 JWT를 발급합니다.
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 회원가입을 처리합니다.
     *
     * <p>클래스 기본값은 readOnly 트랜잭션이지만, 저장 작업이 필요하므로
     * 메서드에 {@code @Transactional}을 다시 붙여 쓰기 트랜잭션으로 실행합니다.</p>
     */
    @Transactional
    public MemberSignUpResponse signUp(MemberSignUpRequest request) {
        if (memberRepository.existsByEmail(request.email())) {
            throw new MemberException(MemberErrorCode.MEMBER_EMAIL_DUPLICATED);
        }

        // 요청 DTO의 값을 사용해 아직 영속화되지 않은 회원 엔티티를 만듭니다.
        Member member = Member.create(
                request.email(),
                passwordEncoder.encode(request.password()),
                request.nickname(),
                request.gender(),
                request.birthDate(),
                request.address()
        );
        Member savedMember = memberRepository.save(member);

        // 요청으로 받은 음식 카테고리 ID들이 모두 실제 DB에 존재하는지 확인합니다.
        List<FoodCategory> foodCategories = foodCategoryRepository.findAllById(request.foodCategoryIds());
        if (foodCategories.size() != request.foodCategoryIds().size()) {
            throw new FoodCategoryException(FoodCategoryErrorCode.FOOD_CATEGORY_NOT_FOUND);
        }

        // 회원과 음식 카테고리 사이의 선호 관계를 연결 엔티티로 만들어 저장합니다.
        List<MemberFoodPreference> preferences = foodCategories.stream()
                .map(foodCategory -> MemberFoodPreference.create(savedMember, foodCategory))
                .toList();
        memberFoodPreferenceRepository.saveAll(preferences);

        // 응답 변환 시 foodPreferences 컬렉션을 바로 사용할 수 있도록 메모리 객체도 동기화합니다.
        savedMember.getFoodPreferences().addAll(preferences);

        AuthTokenResponse token = jwtTokenProvider.generateAccessToken(savedMember);
        return memberConverter.toSignUpResponse(savedMember, token);
    }

    /**
     * 회원 기본 정보를 조회합니다.
     */
    public MemberResponse getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .map(memberConverter::toResponse)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
    }

    /**
     * 회원 포인트만 조회합니다.
     */
    public MemberPointResponse getMyPoints(Long memberId) {
        Member member = getMemberEntity(memberId);
        return memberConverter.toPointResponse(member);
    }

    /**
     * 마이페이지 화면에 필요한 회원 요약 정보를 조회합니다.
     */
    public MemberMyPageResponse getMyPage(Long memberId) {
        Member member = getMemberEntity(memberId);
        long writtenReviewCount = reviewRepository.countByMember_Id(memberId);
        long completedMissionCount = memberMissionRepository.countByMember_IdAndStatus(
                memberId,
                MemberMissionStatus.COMPLETED
        );

        return memberConverter.toMyPageResponse(member, writtenReviewCount, completedMissionCount);
    }

    /**
     * 완료 상태의 회원 미션 개수를 조회합니다.
     */
    public MemberCompletedMissionCountResponse getCompletedMissionCount(Long memberId) {
        getMemberEntity(memberId);
        long count = memberMissionRepository.countByMember_IdAndStatus(memberId, MemberMissionStatus.COMPLETED);
        return memberConverter.toCompletedMissionCountResponse(memberId, count);
    }

    /**
     * 회원 존재 여부 확인과 엔티티 조회를 함께 처리하는 공통 메서드입니다.
     */
    private Member getMemberEntity(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
    }
}
