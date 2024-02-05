package com.example.tingle.auth.service.impl;

import com.example.tingle.auth.dto.CustomOAuth2User;
import com.example.tingle.auth.dto.Request.SignUpRequest;
import com.example.tingle.auth.dto.Response.BasicSingupLoadResponse;
import com.example.tingle.auth.entity.Member;
import com.example.tingle.auth.repository.MemberRepository;
import com.example.tingle.auth.service.AuthService;
import com.example.tingle.star.entity.StarEntity;
import com.example.tingle.star.repository.StarRepository;
import com.example.tingle.user.entity.UserEntity;
import com.example.tingle.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final StarRepository starRepository;
    private final MemberRepository memberRepository;
    private final UserRepository userRepository;
    @Override
    public String singup(SignUpRequest signUpRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof CustomOAuth2User)) {
            return "인증 정보가 없습니다.";
        }

        CustomOAuth2User userDetails = (CustomOAuth2User) authentication.getPrincipal();
        String socialId = userDetails.getName();
        Member member = memberRepository.findBySocialId(socialId).orElse(null);
        boolean isExist = memberRepository.existsByUsername(signUpRequest.getUsername());
        if (isExist) {
            return "이미 유저가 존재합니다";
        }
        member.realMemeber();

        StarEntity star = StarEntity.builder()
                .email(signUpRequest.getEmail())
                .name(signUpRequest.getName())
                .username(signUpRequest.getUsername())
                .build();

        UserEntity user = UserEntity.builder()
                .email(signUpRequest.getEmail())
                .name(signUpRequest.getName())
                .username(signUpRequest.getUsername())
                .build();


        starRepository.save(star);
        userRepository.save(user);

        return "ok";
    }

    @Override
    public BasicSingupLoadResponse loadBasicSingup() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            CustomOAuth2User userDetails = (CustomOAuth2User) authentication.getPrincipal();
            System.out.println(userDetails.getEmail());
            System.out.println(userDetails.getName());
            return BasicSingupLoadResponse.builder()
                    .email(userDetails.getEmail())
                    .name((String) userDetails.getAttributes().get("name"))
                    .build();
        }

        return null;
    }
}
