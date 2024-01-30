package com.example.tingle.cofing;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {
    // Swagger-UI 3.x http://localhost:8080/{api-root}/swagger-ui/index.html

    //전체 API
    @Bean
    public GroupedOpenApi allApis() {
        return GroupedOpenApi.builder()
                .group("All APIs")
                .pathsToMatch("/**")
                .build();
    }

     //팔로우 API
    @Bean
    public GroupedOpenApi followApis() {
        return GroupedOpenApi.builder()
                .group("Follow APIs")
                .pathsToMatch("/follow/**")
                .build();
    }

    //Swagger UI
    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("Tingle API Docs")
                        .description("<h3>Tingle 프로젝트 API입니다.<h3>")
                        .version("1.0")
                );
    }
}