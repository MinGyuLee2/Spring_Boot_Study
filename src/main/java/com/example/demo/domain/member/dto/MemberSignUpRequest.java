package com.example.demo.domain.member.dto;

import com.example.demo.domain.member.enums.MemberGender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

/**
 * 회원가입 요청 DTO입니다.
 *
 * <p>record는 불변 객체를 간결하게 만들 때 사용합니다.
 * 각 필드의 검증 어노테이션은 컨트롤러의 {@code @Valid}와 함께 동작합니다.</p>
 */
public record MemberSignUpRequest(
        // 올바른 이메일 형식이며 100자를 넘지 않아야 합니다.
        @NotBlank
        @Email
        @Size(max = 100)
        String email,

        // 비밀번호는 비어 있을 수 없고 8자 이상이어야 합니다.
        @NotBlank
        @Size(min = 8, max = 255)
        String password,

        // 화면에 표시될 닉네임입니다.
        @NotBlank
        @Size(max = 20)
        String nickname,

        // 성별은 null이면 안 됩니다.
        @NotNull
        MemberGender gender,

        // 생년월일은 오늘보다 과거 날짜여야 합니다.
        @NotNull
        @Past
        LocalDate birthDate,

        // 주소는 비어 있을 수 없습니다.
        @NotBlank
        @Size(max = 100)
        String address,

        // 최소 하나 이상의 음식 카테고리를 선택해야 합니다.
        @NotEmpty
        List<Long> foodCategoryIds
) {
}
