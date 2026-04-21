package com.example.demo.domain.review.service;

import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.exception.MemberErrorCode;
import com.example.demo.domain.member.exception.MemberException;
import com.example.demo.domain.member.repository.MemberRepository;
import com.example.demo.domain.mission.entity.MemberMission;
import com.example.demo.domain.mission.exception.MemberMissionErrorCode;
import com.example.demo.domain.mission.exception.MemberMissionException;
import com.example.demo.domain.mission.repository.MemberMissionRepository;
import com.example.demo.domain.review.converter.ReviewConverter;
import com.example.demo.domain.review.dto.CreateReviewRequest;
import com.example.demo.domain.review.dto.ReviewResponse;
import com.example.demo.domain.review.entity.Review;
import com.example.demo.domain.review.entity.ReviewPhoto;
import com.example.demo.domain.review.exception.ReviewErrorCode;
import com.example.demo.domain.review.exception.ReviewException;
import com.example.demo.domain.review.repository.ReviewPhotoRepository;
import com.example.demo.domain.review.repository.ReviewRepository;
import com.example.demo.domain.store.entity.Store;
import com.example.demo.domain.store.exception.StoreErrorCode;
import com.example.demo.domain.store.exception.StoreException;
import com.example.demo.domain.store.repository.StoreRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    // 리뷰 본문 엔티티를 저장하고 조회하는 Repository입니다.
    private final ReviewRepository reviewRepository;

    // 리뷰 사진 엔티티를 저장하는 Repository입니다.
    private final ReviewPhotoRepository reviewPhotoRepository;

    // 리뷰 작성 회원 존재 여부를 확인하고 엔티티를 가져옵니다.
    private final MemberRepository memberRepository;

    // 리뷰가 작성될 가게 존재 여부를 확인하고 엔티티를 가져옵니다.
    private final StoreRepository storeRepository;

    // 리뷰 작성 대상 미션이 회원 본인의 미션인지 확인합니다.
    private final MemberMissionRepository memberMissionRepository;

    // Review 엔티티를 응답 DTO로 변환합니다.
    private final ReviewConverter reviewConverter;

    /**
     * 리뷰 ID로 단건 조회합니다.
     */
    public ReviewResponse getReview(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .map(reviewConverter::toResponse)
                .orElseThrow(() -> new ReviewException(ReviewErrorCode.REVIEW_NOT_FOUND));
    }

    /**
     * 특정 가게에 리뷰를 작성합니다.
     *
     * <p>리뷰와 리뷰 사진을 저장해야 하므로 쓰기 트랜잭션으로 실행합니다.</p>
     */
    @Transactional
    public ReviewResponse createReview(Long memberId, Long storeId, CreateReviewRequest request) {
        // 리뷰 작성자와 대상 가게가 실제 존재하는지 확인합니다.
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreException(StoreErrorCode.STORE_NOT_FOUND));

        // 회원이 해당 가게의 미션을 수행한 기록을 기준으로 리뷰 작성 권한을 확인합니다.
        validateMemberMission(memberId, storeId, request.memberMissionId());

        Review review = reviewRepository.save(Review.create(member, store, request.rating(), request.content()));

        // imageUrls가 null이면 빈 리스트로 처리해서 이후 stream 처리를 단순하게 만듭니다.
        List<String> imageUrls = request.imageUrls() == null ? List.of() : request.imageUrls();
        List<ReviewPhoto> reviewPhotos = imageUrls.stream()
                .map(imageUrl -> ReviewPhoto.create(review, imageUrl))
                .toList();

        reviewPhotoRepository.saveAll(reviewPhotos);

        // 응답 변환 시 저장한 사진 목록이 바로 포함되도록 연관 컬렉션에도 추가합니다.
        reviewPhotos.forEach(review::addReviewPhoto);

        return reviewConverter.toResponse(review);
    }

    /**
     * 회원이 리뷰를 작성하려는 가게의 미션을 실제로 가지고 있는지 확인합니다.
     */
    private void validateMemberMission(Long memberId, Long storeId, Long memberMissionId) {
        MemberMission memberMission = memberMissionRepository.findByIdAndMember_Id(memberMissionId, memberId)
                .orElseThrow(() -> new MemberMissionException(MemberMissionErrorCode.MEMBER_MISSION_NOT_FOUND));

        // 회원 미션의 가게와 리뷰 대상 가게가 다르면 잘못된 요청으로 판단합니다.
        if (!memberMission.getMission().getStore().getId().equals(storeId)) {
            throw new MemberMissionException(MemberMissionErrorCode.MEMBER_MISSION_NOT_FOUND);
        }
    }
}
