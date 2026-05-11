package com.example.demo.domain.review.service;

import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.exception.MemberErrorCode;
import com.example.demo.domain.member.exception.MemberException;
import com.example.demo.domain.member.repository.MemberRepository;
import com.example.demo.domain.mission.entity.MemberMission;
import com.example.demo.domain.mission.enums.MemberMissionStatus;
import com.example.demo.domain.mission.exception.MemberMissionErrorCode;
import com.example.demo.domain.mission.exception.MemberMissionException;
import com.example.demo.domain.mission.repository.MemberMissionRepository;
import com.example.demo.domain.review.converter.ReviewConverter;
import com.example.demo.domain.review.dto.CreateReviewRequest;
import com.example.demo.domain.review.dto.MyReviewCursorRequest;
import com.example.demo.domain.review.dto.MyReviewCursorResponse;
import com.example.demo.domain.review.dto.MyReviewResponse;
import com.example.demo.domain.review.dto.ReviewResponse;
import com.example.demo.domain.review.dto.StoreReviewPageResponse;
import com.example.demo.domain.review.dto.StoreReviewResponse;
import com.example.demo.domain.review.entity.Review;
import com.example.demo.domain.review.entity.ReviewPhoto;
import com.example.demo.domain.review.enums.ReviewSortType;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
     * 내가 작성한 리뷰 목록을 커서 기반으로 조회합니다.
     */
    public MyReviewCursorResponse getMyReviews(MyReviewCursorRequest request) {
        validateMember(request.memberId());

        ReviewCursor cursor = parseCursor(request.cursor(), request.sortType());
        List<Review> reviews = findMyReviewsByCursor(request, cursor);

        boolean hasNext = reviews.size() > request.size();
        List<Review> currentReviews = reviews.stream()
                .limit(request.size())
                .toList();
        List<MyReviewResponse> responses = currentReviews.stream()
                .map(reviewConverter::toMyReviewResponse)
                .toList();
        String nextCursor = hasNext && !currentReviews.isEmpty()
                ? createNextCursor(currentReviews.get(currentReviews.size() - 1), request.sortType())
                : null;

        return new MyReviewCursorResponse(responses, hasNext, nextCursor, request.size());
    }

    /**
     * 특정 가게에 작성된 리뷰 목록을 페이징 조회합니다.
     */
    public StoreReviewPageResponse getStoreReviews(Long storeId, int page, int size) {
        validateStore(storeId);

        Page<Review> reviews = reviewRepository.findReviewsByStoreId(
                storeId,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"))
        );

        List<StoreReviewResponse> reviewResponses = reviews.stream()
                .map(reviewConverter::toStoreReviewResponse)
                .toList();

        return new StoreReviewPageResponse(
                reviewResponses,
                reviews.getNumber(),
                reviews.getSize(),
                reviews.getTotalElements(),
                reviews.getTotalPages()
        );
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
     * 정렬 기준에 맞는 커서 조회 쿼리를 선택합니다.
     */
    private List<Review> findMyReviewsByCursor(MyReviewCursorRequest request, ReviewCursor cursor) {
        PageRequest pageRequest = PageRequest.of(0, request.size() + 1);

        return switch (request.sortType()) {
            case ID_ASC -> reviewRepository.findMyReviewsByIdAsc(
                    request.memberId(),
                    cursor.reviewId(),
                    pageRequest
            );
            case ID_DESC -> reviewRepository.findMyReviewsByIdDesc(
                    request.memberId(),
                    cursor.reviewId(),
                    pageRequest
            );
            case RATING_ASC -> reviewRepository.findMyReviewsByRatingAsc(
                    request.memberId(),
                    cursor.rating(),
                    cursor.reviewId(),
                    pageRequest
            );
            case RATING_DESC -> reviewRepository.findMyReviewsByRatingDesc(
                    request.memberId(),
                    cursor.rating(),
                    cursor.reviewId(),
                    pageRequest
            );
        };
    }

    /**
     * 요청 커서 문자열을 정렬 기준에 맞게 해석합니다.
     */
    private ReviewCursor parseCursor(String cursor, ReviewSortType sortType) {
        if (cursor == null || cursor.isBlank()) {
            return new ReviewCursor(null, null);
        }

        try {
            if (sortType == ReviewSortType.ID_ASC || sortType == ReviewSortType.ID_DESC) {
                return new ReviewCursor(Long.parseLong(cursor), null);
            }

            String[] parts = cursor.split(":");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Rating cursor must be rating:reviewId");
            }
            return new ReviewCursor(Long.parseLong(parts[1]), Integer.parseInt(parts[0]));
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Invalid review cursor", exception);
        }
    }

    /**
     * 다음 요청에 사용할 커서 문자열을 생성합니다.
     */
    private String createNextCursor(Review review, ReviewSortType sortType) {
        if (sortType == ReviewSortType.RATING_ASC || sortType == ReviewSortType.RATING_DESC) {
            return review.getRating() + ":" + review.getId();
        }

        return String.valueOf(review.getId());
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

        // 리뷰는 완료된 미션에 대해서만 작성할 수 있습니다.
        if (memberMission.getStatus() != MemberMissionStatus.COMPLETED) {
            throw new MemberMissionException(MemberMissionErrorCode.MEMBER_MISSION_NOT_COMPLETED);
        }
    }

    /**
     * 가게 존재 여부를 확인합니다.
     */
    private void validateStore(Long storeId) {
        if (!storeRepository.existsById(storeId)) {
            throw new StoreException(StoreErrorCode.STORE_NOT_FOUND);
        }
    }

    /**
     * 회원 존재 여부를 확인합니다.
     */
    private void validateMember(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new MemberException(MemberErrorCode.MEMBER_NOT_FOUND);
        }
    }

    private record ReviewCursor(
            Long reviewId,
            Integer rating
    ) {
    }
}
