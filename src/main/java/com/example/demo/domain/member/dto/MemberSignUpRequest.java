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

public record MemberSignUpRequest(
        @NotBlank
        @Email
        @Size(max = 100)
        String email,

        @NotBlank
        @Size(min = 8, max = 255)
        String password,

        @NotBlank
        @Size(max = 20)
        String nickname,

        @NotNull
        MemberGender gender,

        @NotNull
        @Past
        LocalDate birthDate,

        @NotBlank
        @Size(max = 100)
        String address,

        @NotEmpty
        List<Long> foodCategoryIds
) {
}
