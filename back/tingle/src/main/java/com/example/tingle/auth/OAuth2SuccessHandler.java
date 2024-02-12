package com.example.tingle.auth;

import com.example.tingle.auth.dto.CustomOAuth2User;
import com.example.tingle.star.repository.StarRepository;
import com.example.tingle.user.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        System.out.println("수고했네 oauth에 성공했네");
        if (Boolean.FALSE.equals(oAuth2User.getIsRealMember())) {
            getRedirectStrategy().sendRedirect(request, response, "http://localhost:5173/signUp");
        } else {
            getRedirectStrategy().sendRedirect(request, response, "http://localhost:5173/selectLoginType");
        }
    }
}