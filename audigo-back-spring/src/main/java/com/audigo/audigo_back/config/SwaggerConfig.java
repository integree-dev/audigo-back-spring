package com.audigo.audigo_back.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("AUDIGO API 명세서 - V1") // API 제목
                .description("Audigo API Web Site With Spring Boot 3.3.12") // API 설명
                .version("1.0.0"); // API 버전
    }
}
