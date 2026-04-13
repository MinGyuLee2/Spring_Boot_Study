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

    private final ReviewRepository reviewRepository;
    private final ReviewPhotoRepository reviewPhotoRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final MemberMissionRepository memberMissionRepository;
    private final ReviewConverter reviewConverter;

    public ReviewResponse getReview(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .map(reviewConverter::toResponse)
                .orElseThrow(() -> new ReviewException(ReviewErrorCode.REVIEW_NOT_FOUND));
    }

    @Transactional
    public ReviewResponse createReview(Long memberId, Long storeId, CreateReviewRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreException(StoreErrorCode.STORE_NOT_FOUND));
        validateMemberMission(memberId, storeId, request.memberMissionId());

        Review review = reviewRepository.save(Review.create(member, store, request.rating(), request.content()));

        List<String> imageUrls = request.imageUrls() == null ? List.of() : request.imageUrls();
        List<ReviewPhoto> reviewPhotos = imageUrls.stream()
                .map(imageUrl -> ReviewPhoto.create(review, imageUrl))
                .toList();

        reviewPhotoRepository.saveAll(reviewPhotos);
        reviewPhotos.forEach(review::addReviewPhoto);

        return reviewConverter.toResponse(review);
    }

    private void validateMemberMission(Long memberId, Long storeId, Long memberMissionId) {
        MemberMission memberMission = memberMissionRepository.findByIdAndMember_Id(memberMissionId, memberId)
                .orElseThrow(() -> new MemberMissionException(MemberMissionErrorCode.MEMBER_MISSION_NOT_FOUND));

        if (!memberMission.getMission().getStore().getId().equals(storeId)) {
            throw new MemberMissionException(MemberMissionErrorCode.MEMBER_MISSION_NOT_FOUND);
        }
    }
}
