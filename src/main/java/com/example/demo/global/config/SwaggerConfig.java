package com.example.demo.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    /**
     * Swagger UI와 OpenAPI 문서에 표시될 기본 정보를 설정합니다.
     *
     * <p>{@code @Bean}으로 등록하면 springdoc-openapi가 이 설정을 읽어서
     * API 문서 화면을 구성합니다.</p>
     */
    @Bean
    public OpenAPI swagger() {
        // Swagger 문서 상단에 표시되는 제목, 설명, 버전 정보입니다.
        Info info = new Info()
                .title("UMC10th")
                .description("10기 Swagger")
                .version("0.0.1");

        String securityScheme = "bearerAuth";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(securityScheme);
        Components components = new Components()
                .addSecuritySchemes(securityScheme, new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT"));

        // Swagger UI의 Authorize 버튼에서 JWT Bearer 토큰을 입력할 수 있도록 설정합니다.
        return new OpenAPI()
                .info(info)
                .addServersItem(new Server().url("/"))
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}
