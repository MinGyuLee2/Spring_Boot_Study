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

    @JsonProperty("isSuccess")
    private final Boolean isSuccess;

    @JsonProperty("code")
    private final String code;

    @JsonProperty("message")
    private final String message;

    @JsonProperty("result")
    private final T result;

    public static <T> ApiResponse<T> onSuccess(T result) {
        return onSuccess(GeneralSuccessCode.OK, result);
    }

    public static <T> ApiResponse<T> onSuccess(BaseSuccessCode code, T result) {
        return new ApiResponse<>(true, code.getCode(), code.getMessage(), result);
    }

    public static <T> ApiResponse<T> onFailure(BaseErrorCode code, T result) {
        return new ApiResponse<>(false, code.getCode(), code.getMessage(), result);
    }

    public static ApiResponse<Void> onFailure(BaseErrorCode code) {
        return onFailure(code, null);
    }
}
