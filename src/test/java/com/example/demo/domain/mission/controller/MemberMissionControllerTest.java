package com.example.demo.domain.mission.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.domain.mission.dto.MemberMissionCompleteResponse;
import com.example.demo.domain.mission.dto.MemberMissionPageResponse;
import com.example.demo.domain.mission.dto.MemberMissionResponse;
import com.example.demo.domain.mission.enums.MemberMissionStatus;
import com.example.demo.domain.mission.service.MemberMissionService;
import com.example.demo.global.exception.GlobalExceptionHandler;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class MemberMissionControllerTest {

    private MemberMissionService memberMissionService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        memberMissionService = mock(MemberMissionService.class);

        MemberMissionController memberMissionController = new MemberMissionController(memberMissionService);

        mockMvc = MockMvcBuilders.standaloneSetup(memberMissionController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void getMemberMissionsReturnsPagedResponse() throws Exception {
        MemberMissionPageResponse response = new MemberMissionPageResponse(
                List.of(new MemberMissionResponse(
                        101L,
                        1L,
                        "10,000원 이상의 식사시 사진 리뷰",
                        500,
                        3L,
                        "반이학생마라탕",
                        MemberMissionStatus.CHALLENGING,
                        LocalDateTime.parse("2026-03-21T10:00:00"),
                        null
                )),
                0,
                10,
                1,
                1
        );

        when(memberMissionService.getMemberMissions(eq(1L), eq(MemberMissionStatus.CHALLENGING), eq(0), eq(10)))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/member-missions")
                        .header("X-Member-Id", "1")
                        .param("status", "CHALLENGING")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value("COMMON200"))
                .andExpect(jsonPath("$.message").value("성공입니다."))
                .andExpect(jsonPath("$.result.memberMissions[0].memberMissionId").value(101))
                .andExpect(jsonPath("$.result.memberMissions[0].missionId").value(1))
                .andExpect(jsonPath("$.result.memberMissions[0].title").value("10,000원 이상의 식사시 사진 리뷰"))
                .andExpect(jsonPath("$.result.memberMissions[0].rewardPoint").value(500))
                .andExpect(jsonPath("$.result.memberMissions[0].storeId").value(3))
                .andExpect(jsonPath("$.result.memberMissions[0].storeName").value("반이학생마라탕"))
                .andExpect(jsonPath("$.result.memberMissions[0].status").value("CHALLENGING"))
                .andExpect(jsonPath("$.result.memberMissions[0].startedAt").value("2026-03-21T10:00:00"))
                .andExpect(jsonPath("$.result.memberMissions[0].completedAt").doesNotExist())
                .andExpect(jsonPath("$.result.page").value(0))
                .andExpect(jsonPath("$.result.size").value(10))
                .andExpect(jsonPath("$.result.totalElements").value(1))
                .andExpect(jsonPath("$.result.totalPages").value(1));

        verify(memberMissionService).getMemberMissions(1L, MemberMissionStatus.CHALLENGING, 0, 10);
    }

    @Test
    void getMemberMissionsReturnsBadRequestWhenQueryParameterIsMissing() throws Exception {
        mockMvc.perform(get("/api/v1/member-missions")
                        .header("X-Member-Id", "1")
                        .param("status", "CHALLENGING")
                        .param("page", "0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(false))
                .andExpect(jsonPath("$.code").value("COMMON400"));

        verifyNoInteractions(memberMissionService);
    }

    @Test
    void completeMissionReturnsSuccessForAuthenticatedOwner() throws Exception {
        MemberMissionCompleteResponse response = new MemberMissionCompleteResponse(
                101L,
                MemberMissionStatus.COMPLETED,
                LocalDateTime.parse("2026-03-21T12:00:00")
        );

        when(memberMissionService.completeMission(eq(1L), eq(101L))).thenReturn(response);

        mockMvc.perform(patch("/api/v1/member-missions/{memberMissionId}/complete", 101L)
                        .header("X-Member-Id", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value("COMMON200"))
                .andExpect(jsonPath("$.result.memberMissionId").value(101))
                .andExpect(jsonPath("$.result.status").value("COMPLETED"))
                .andExpect(jsonPath("$.result.completedAt").value("2026-03-21T12:00:00"));

        verify(memberMissionService).completeMission(1L, 101L);
    }
}
