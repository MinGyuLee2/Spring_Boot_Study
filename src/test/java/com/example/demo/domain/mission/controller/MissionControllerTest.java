package com.example.demo.domain.mission.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.domain.mission.dto.AvailableMissionPageResponse;
import com.example.demo.domain.mission.dto.AvailableMissionResponse;
import com.example.demo.domain.mission.service.MissionService;
import com.example.demo.global.exception.GlobalExceptionHandler;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class MissionControllerTest {

    private MissionService missionService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        missionService = mock(MissionService.class);

        MissionController missionController = new MissionController(missionService);

        mockMvc = MockMvcBuilders.standaloneSetup(missionController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void getAvailableMissionsReturnsPagedResponse() throws Exception {
        AvailableMissionPageResponse response = new AvailableMissionPageResponse(
                List.of(new AvailableMissionResponse(
                        1L,
                        "10,000원 이상의 식사시 사진 리뷰",
                        "식사 후 사진 리뷰 작성",
                        500,
                        3L,
                        "반이학생마라탕",
                        "중식당",
                        1L,
                        "안암동"
                )),
                0,
                10,
                1,
                1
        );

        when(missionService.getAvailableMissions(eq(1L), eq(1L), eq(0), eq(10))).thenReturn(response);

        mockMvc.perform(get("/api/v1/missions/available")
                        .header("X-Member-Id", "1")
                        .param("regionId", "1")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value("COMMON200"))
                .andExpect(jsonPath("$.message").value("성공입니다."))
                .andExpect(jsonPath("$.result.missions[0].missionId").value(1))
                .andExpect(jsonPath("$.result.missions[0].storeName").value("반이학생마라탕"))
                .andExpect(jsonPath("$.result.missions[0].regionName").value("안암동"))
                .andExpect(jsonPath("$.result.page").value(0))
                .andExpect(jsonPath("$.result.size").value(10))
                .andExpect(jsonPath("$.result.totalElements").value(1))
                .andExpect(jsonPath("$.result.totalPages").value(1));

        verify(missionService).getAvailableMissions(1L, 1L, 0, 10);
    }

    @Test
    void getAvailableMissionsReturnsBadRequestWhenQueryParameterIsMissing() throws Exception {
        mockMvc.perform(get("/api/v1/missions/available")
                        .header("X-Member-Id", "1")
                        .param("regionId", "1")
                        .param("page", "0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(false))
                .andExpect(jsonPath("$.code").value("COMMON400"));

        verifyNoInteractions(missionService);
    }
}
