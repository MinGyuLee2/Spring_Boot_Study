package com.example.demo.global.apiPayload;

import com.example.demo.global.apiPayload.code.BaseErrorCode;
import com.example.demo.global.apiPayload.code.BaseSuccessCode;
import com.example.demo.global.apiPayload.code.GeneralSuccessCode;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class ApiResponse<T> {

    // 요청 처리 성공 여부입니다. 성공이면 true, 실패이면 false입니다.
    @JsonProperty("isSuccess")
    private final Boolean isSuccess;

    // 프론트엔드나 클라이언트가 분기 처리할 때 사용할 애플리케이션 응답 코드입니다.
    @JsonProperty("code")
    private final String code;

    // 응답 코드에 대응하는 사람이 읽을 수 있는 메시지입니다.
    @JsonProperty("message")
    private final String message;

    // 실제 응답 데이터입니다. 응답 데이터가 없으면 null이 들어갈 수 있습니다.
    @JsonProperty("result")
    private final T result;

    /**
     * 가장 기본적인 성공 응답을 만듭니다.
     *
     * <p>성공 코드를 따로 넘기지 않으면 GeneralSuccessCode.OK를 사용합니다.</p>
     */
    public static <T> ApiResponse<T> onSuccess(T result) {
        return onSuccess(GeneralSuccessCode.OK, result);
    }

    /**
     * 원하는 성공 코드를 지정해서 성공 응답을 만듭니다.
     *
     * <p>예: 생성 API에서는 OK 대신 CREATED를 넘겨 201 응답 의미를 표현할 수 있습니다.</p>
     */
    public static <T> ApiResponse<T> onSuccess(BaseSuccessCode code, T result) {
        return new ApiResponse<>(true, code.getCode(), code.getMessage(), result);
    }

    /**
     * 실패 응답을 만듭니다.
     *
     * <p>필요하다면 result에 추가 에러 정보를 담을 수 있습니다.</p>
     */
    public static <T> ApiResponse<T> onFailure(BaseErrorCode code, T result) {
        return new ApiResponse<>(false, code.getCode(), code.getMessage(), result);
    }

    /**
     * 별도의 결과 데이터가 없는 실패 응답을 만듭니다.
     */
    public static ApiResponse<Void> onFailure(BaseErrorCode code) {
        return onFailure(code, null);
    }
}
