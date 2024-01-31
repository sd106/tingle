package com.example.tingle.user.controller;

import com.example.tingle.user.dto.CustomStarDetails;
import com.example.tingle.user.dto.LoginDto;
import com.example.tingle.user.entity.StarEntity;
import com.example.tingle.user.repository.StarRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@Slf4j
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final StarRepository starRepository;

    // 생성자를 통해 주입받도록 설정
    @Autowired
    public LoginController(AuthenticationManager authenticationManager, StarRepository starRepository) {
        this.authenticationManager = authenticationManager;
        this.starRepository = starRepository;
    }

    @PostMapping("/users/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginDto loginDto, HttpServletResponse response) {

        HttpStatus status = HttpStatus.ACCEPTED;
        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            System.out.println("loginDto.getUsername() = " + loginDto.getUsername());
            System.out.println("loginDto.getPassword() = " + loginDto.getPassword());

            if (starRepository.existsByUsername(loginDto.getUsername())) {
                StarEntity star = starRepository.findByUsername(loginDto.getUsername());
                resultMap.put("starId", star.getId());
                resultMap.put("username", star.getUsername());
                resultMap.put("picture", star.getPicture());
                resultMap.put("email", star.getEmail());
                resultMap.put("role", star.getRole());
                resultMap.put("provider", star.getProvider());
                status = HttpStatus.OK;

                new CustomStarDetails(star);
            } else {
                resultMap.put("message", "아이디 또는 패스워드를 확인해주세요 !");
                status = HttpStatus.UNAUTHORIZED;
            }

        } catch (AuthenticationException e) {
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
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
