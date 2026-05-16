package com.example.demo.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
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

        // OpenAPI 객체에 문서 정보와 서버 URL을 합쳐서 반환합니다.
        return new OpenAPI()
                .info(info)
                .addServersItem(new Server().url("/"));
    }
}
