package com.example.tingle.auth.controller;

import com.example.tingle.auth.dto.CustomOAuth2User;
import com.example.tingle.auth.dto.Request.SignUpRequest;
import com.example.tingle.auth.dto.Response.BasicSingupLoadResponse;
import com.example.tingle.auth.service.AuthService;
import com.example.tingle.star.dto.response.StarDTO;
import com.example.tingle.star.entity.StarEntity;
import com.example.tingle.star.repository.StarRepository;
import com.example.tingle.user.dto.UserDto;
import com.example.tingle.user.entity.UserEntity;
import com.example.tingle.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserRepository userRepository;
    private final StarRepository starRepository;

    // 인증 절차는 끝났기 때문에 프런트 store에 넣어줄 정보만 return 하도록 하자
    @PostMapping("/login/fan/{provider}")
    public ResponseEntity<?> loginFan(@PathVariable String provider) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomOAuth2User userDetails = (CustomOAuth2User) authentication.getPrincipal();

        String email = userDetails.getEmail();

        UserEntity user = userRepository.findByEmailAndProvider(email, provider).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("없다 가라");
        }
        UserDto userDto = UserDto.mapToDTO(user);

        return ResponseEntity.ok(userDto);
    }


    @PostMapping("/login/star/{provider}")
    public ResponseEntity<?> loginStar(@PathVariable String provider) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        CustomOAuth2User userDetails = (CustomOAuth2User) authentication.getPrincipal();

        String email = userDetails.getEmail();

        StarEntity star = starRepository.findByEmailAndProvider(email, provider).orElse(null);
        if (star == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("없다 가라");
        }
        StarDTO starDTO = StarDTO.mapToDto(star);

        return ResponseEntity.ok(starDTO);
    }

    @GetMapping("/member/info")
    public ResponseEntity<?> getCurrentUser() {
        BasicSingupLoadResponse response = authService.loadBasicSingup();
        if (response != null) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("없다 가라");
    }

    @PostMapping("/member/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest) {
        String response = authService.singup(signUpRequest);

        if (response.equals("ok")) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

}