package com.finance.onlinebanking.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI(@Value("${springdoc.version}") String springdocVersion) {
        Info info = new Info()
                .title("Online Banking Project API")
                .version(springdocVersion)
                .description("온라인 뱅킹 프로젝트 API 명세서");

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
