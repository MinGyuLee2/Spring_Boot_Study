package com.example.demo.domain.store.controller;

import com.example.demo.domain.review.dto.CreateReviewRequest;
import com.example.demo.domain.review.dto.ReviewResponse;
import com.example.demo.domain.review.dto.StoreReviewPageResponse;
import com.example.demo.domain.review.service.ReviewService;
import com.example.demo.global.apiPayload.ApiResponse;
import com.example.demo.global.apiPayload.code.GeneralErrorCode;
import com.example.demo.global.apiPayload.code.GeneralSuccessCode;
import com.example.demo.global.exception.DomainException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stores")
public class StoreController {

    // 가게에 리뷰를 작성하는 기능은 ReviewService에 위임합니다.
    private final ReviewService reviewService;

    /**
     * 특정 가게에 작성된 리뷰 목록을 조회합니다.
     *
     * <p>page는 0부터 시작하고, size는 한 페이지에 담을 데이터 개수입니다.</p>
     */
    @GetMapping("/{storeId}/reviews")
    public ApiResponse<StoreReviewPageResponse> getStoreReviews(
            @PathVariable Long storeId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        validatePageRequest(page, size);

        return ApiResponse.onSuccess(reviewService.getStoreReviews(storeId, page, size));
    }

    /**
     * 특정 가게에 리뷰를 작성합니다.
     *
     * <p>REST 관점에서 리뷰는 가게 하위 리소스로 볼 수 있으므로
     * /stores/{storeId}/reviews 경로를 사용합니다.</p>
     */
    @Operation(summary = "가게 리뷰 작성")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "리뷰 작성 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 필요")
    })
    @PostMapping(value = "/{storeId}/reviews", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<ReviewResponse>> createReview(
            @RequestHeader(value = "X-Member-Id", defaultValue = "1") Long memberId,
            @PathVariable Long storeId,
            @Valid @RequestBody CreateReviewRequest request
    ) {
        ReviewResponse response = reviewService.createReview(memberId, storeId, request);
        return ResponseEntity.status(GeneralSuccessCode.CREATED.getHttpStatus())
                .body(ApiResponse.onSuccess(GeneralSuccessCode.CREATED, response));
    }

    /**
     * 페이징 값의 기본 범위를 검증합니다.
     */
    private void validatePageRequest(Integer page, Integer size) {
        if (page == null || size == null || page < 0 || size < 1) {
            throw new DomainException(GeneralErrorCode.BAD_REQUEST);
        }
    }
}
