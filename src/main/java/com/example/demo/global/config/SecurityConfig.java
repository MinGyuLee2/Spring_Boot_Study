package com.example.demo.global.config;

import com.example.demo.global.apiPayload.ApiResponse;
import com.example.demo.global.apiPayload.code.BaseErrorCode;
import com.example.demo.global.apiPayload.code.GeneralErrorCode;
import com.example.demo.global.apiPayload.code.GeneralSuccessCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * BCrypt는 비밀번호마다 다른 salt를 사용해 해시를 만듭니다.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/api/v1/members/signup",
                                "/login",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/h2-console/**",
                                "/error"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginProcessingUrl("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .successHandler((request, response, authentication) -> writeSuccess(
                                response,
                                OBJECT_MAPPER,
                                Map.of("email", authentication.getName())
                        ))
                        .failureHandler((request, response, exception) ->
                                writeError(response, OBJECT_MAPPER, GeneralErrorCode.UNAUTHORIZED))
                        .permitAll()
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint((request, response, exception) ->
                                writeError(response, OBJECT_MAPPER, GeneralErrorCode.UNAUTHORIZED))
                        .accessDeniedHandler((request, response, exception) ->
                                writeError(response, OBJECT_MAPPER, GeneralErrorCode.FORBIDDEN))
                );

        return http.build();
    }

    private static void writeSuccess(
            HttpServletResponse response,
            ObjectMapper objectMapper,
            Map<String, String> result
    ) throws IOException {
        response.setStatus(GeneralSuccessCode.OK.getHttpStatus().value());
        writeJson(response, objectMapper, ApiResponse.onSuccess(result));
    }

    private static void writeError(
            HttpServletResponse response,
            ObjectMapper objectMapper,
            BaseErrorCode errorCode
    ) throws IOException {
        response.setStatus(errorCode.getHttpStatus().value());
        writeJson(response, objectMapper, ApiResponse.onFailure(errorCode));
    }

    private static void writeJson(
            HttpServletResponse response,
            ObjectMapper objectMapper,
            ApiResponse<?> apiResponse
    ) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        objectMapper.writeValue(response.getWriter(), apiResponse);
    }
}
