package com.example.tingle.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// CorsConfig.java
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true) // 브라우저가 인증 정보(예: 쿠키, HTTP 인증 관련 헤더, TLS 클라이언트 인증 등)를 함께 전송할 수 있도록 하는 역할
                .allowedOrigins("http://localhost:5173", "http://my-tingle.us/", "https://my-tingle.us/", "https://i10d106.p.ssafy.io/", "http://i10d106.p.ssafy.io:8080")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*");
    }
}

// , "http://my-tingle.us/", "https://my-tingle.us/", "http://localhost:8080/", "http://localhost:8080:8080"