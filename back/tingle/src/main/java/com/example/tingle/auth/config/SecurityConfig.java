package com.example.tingle.auth.config;

import com.example.tingle.auth.oauth.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws  Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .rememberMe(AbstractHttpConfigurer::disable)
//                .cors((auth) -> auth.configure(http))
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/**","/logout","/login","/users/login", "/signUp", "/signUp/**", "/users/new", "/oauth").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/my/**").hasAnyRole("ADMIN", "FAN", "STAR")
                        .anyRequest().authenticated())
                .logout(logout -> logout
                        .invalidateHttpSession(true) // 세션 무효화
                        .deleteCookies("JSESSIONID") // 세션 쿠키 삭제
                        .logoutSuccessUrl("/")
                        .logoutSuccessHandler(((request, response, authentication) -> {
                            response.sendRedirect("http://localhost:5173/");
                        }))
                )
                .oauth2Login(oauth2Login ->
                        oauth2Login.userInfoEndpoint(userInfoEndpointConfig ->
                                        userInfoEndpointConfig.userService(customOAuth2UserService))
                                .successHandler((request, response, authentication) -> {
                                    // 로그인 성공 시 호출되는 핸들러
                                    response.sendRedirect("http://localhost:5173/"); // 리다이렉트할 URL
                                })
                )
                .sessionManagement((auth) -> auth   // 다중 로그인
                        .maximumSessions(1) //다중 로그인 갯수
                        .maxSessionsPreventsLogin(true)) //true = 로그인 안됨, false = 기존 로그인 로그아웃 시키고 로그인 함;
                .sessionManagement((auth) -> auth
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                        .sessionFixation().changeSessionId())
                .addFilterBefore(corsFilter(), CorsFilter.class);

        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:5173"); // 프론트엔드 도메인에 맞게 수정
        config.addAllowedHeader("*");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        config.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
