package com.example.tingle.auth.config;

import com.example.tingle.auth.OAuth2SuccessHandler;
import com.example.tingle.auth.oauth.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
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

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler OAuth2SuccessHandler;

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
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
                .csrf(AbstractHttpConfigurer::disable) // 세션 로그인 방식을 사용하니까 csrf 토큰을 사용해야함 근데 post요청을 위해 disable
                .rememberMe(AbstractHttpConfigurer::disable)
//                .cors((auth) -> auth.configure(http)) // spring MVC CORS config를 사용중이니 인증절차에서 cors 설정을 안함
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/**", "/logout", "/login", "/users/login", "/signUp", "/signUp/**", "/users/new", "/oauth").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
//                        .requestMatchers("/my/**").hasAnyRole("ADMIN", "FAN", "STAR")
                        .anyRequest().authenticated())
                // 로그아웃 설정 (그냥 /logout GET 이나 POST 요청을 하면 로그아웃 됨)
                .logout(logout -> logout
                        .logoutUrl("/logout") // 로그아웃 url
                        .invalidateHttpSession(true) // 세션 무효화
                        .deleteCookies("JSESSIONID") // 세션 쿠키 삭제
                        .logoutSuccessUrl("/")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.sendRedirect("http://localhost:5173/");
                        })
                )
                // OAuth2 로그인 설정
                .oauth2Login(oauth2Login -> oauth2Login
                        // provider으로 부터 인증이 완료되면 사용자를 불러오는 서비스 호출
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(customOAuth2UserService))
                        .successHandler(OAuth2SuccessHandler)
                )
                // 세션 설정
                .sessionManagement((auth) -> auth
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS) // 세션을 항상 생성
                        .sessionFixation().changeSessionId() // 로그인 할 때 마다 세션 아이디 바꿔버림 ( 세션 고정 공격 대비 )
                        .maximumSessions(1) // 다중 로그인 갯수 ( 넷플릭스같이 공유 로그인 갯수 )
                        .maxSessionsPreventsLogin(false)); // 그 갯수가 넘으면 true = 로그인 안됨, false = 기존 로그인 로그아웃 시키고 로그인 함;

        return http.build();
    }

}
