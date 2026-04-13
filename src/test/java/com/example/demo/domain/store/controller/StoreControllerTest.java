package com.example.demo.domain.store.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.domain.review.dto.ReviewResponse;
import com.example.demo.domain.review.service.ReviewService;
import com.example.demo.global.exception.GlobalExceptionHandler;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class StoreControllerTest {

    private ReviewService reviewService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        reviewService = mock(ReviewService.class);

        StoreController storeController = new StoreController(reviewService);

        mockMvc = MockMvcBuilders.standaloneSetup(storeController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void createReviewReturnsCreatedForAuthenticatedMember() throws Exception {
        ReviewResponse response = new ReviewResponse(
                1L,
                1L,
                3L,
                5,
                "음식이 맛있고 양도 많았어요.",
                List.of("https://cdn.example.com/reviews/review1.jpg")
        );

        when(reviewService.createReview(eq(1L), eq(3L), eq(new com.example.demo.domain.review.dto.CreateReviewRequest(
                5,
                "음식이 맛있고 양도 많았어요.",
                List.of("https://cdn.example.com/reviews/review1.jpg"),
                101L
        )))).thenReturn(response);

        mockMvc.perform(post("/api/v1/stores/{storeId}/reviews", 3L)
                        .header("X-Member-Id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "rating": 5,
                                  "content": "음식이 맛있고 양도 많았어요.",
                                  "imageUrls": [
                                    "https://cdn.example.com/reviews/review1.jpg"
                                  ],
                                  "memberMissionId": 101
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value("COMMON201"))
                .andExpect(jsonPath("$.result.reviewId").value(1))
                .andExpect(jsonPath("$.result.memberId").value(1))
                .andExpect(jsonPath("$.result.storeId").value(3))
                .andExpect(jsonPath("$.result.rating").value(5));

        verify(reviewService).createReview(eq(1L), eq(3L), eq(new com.example.demo.domain.review.dto.CreateReviewRequest(
                5,
                "음식이 맛있고 양도 많았어요.",
                List.of("https://cdn.example.com/reviews/review1.jpg"),
                101L
        )));
    }
}
