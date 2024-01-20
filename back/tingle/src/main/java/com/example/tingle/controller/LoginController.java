package com.example.tingle.controller;

import com.example.tingle.dto.LoginDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@Slf4j
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/users/login")
    public String login(@RequestBody LoginDto loginDto) {
        try {
            System.out.println("loginDto.getEmail() = " + loginDto.getEmail());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("authentication.getPrincipal().getClass() = " + authentication.getPrincipal().getClass());
            // 여기서 토큰을 생성하거나 다른 작업을 수행할 수 있음
            return "ok";
        } catch (AuthenticationException e) {
//            System.out.println("loginDto.getEmail() = " + loginDto.getEmail());

            // 인증 실패 시 예외 처리
            return "error";
        }
    }

    @PostMapping("/users/logout")
    public String logout() {
        // 현재 사용자의 인증 정보를 가져옵니다.
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // 사용자가 인증되어 있다면 로그아웃 처리를 합니다.
        if (auth != null) {
            // 인증 정보를 초기화하고 세션을 만료시킵니다.
            SecurityContextHolder.getContext().setAuthentication(null);
        }

        // 로그아웃 후 리다이렉션할 경로를 설정합니다.
        return "logoutOK";
    }
}
