package com.example.tingle.user.controller;

import com.nimbusds.openid.connect.sdk.claims.UserInfo;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/oauth2")
public class OAuthController {

    @GetMapping("/google") // @AuthenticationPrincipal 어노테이션을 사용하여 현재 인증된 사용자 정보를 가져온다.
    public ResponseEntity<Map<String, Object>> getGoogleUser(@AuthenticationPrincipal OAuth2User user) {
        System.out.println("user = " + user);

        HttpStatus status = HttpStatus.ACCEPTED;
        Map<String, Object> resultMap = new HashMap<String, Object>();
//        int id = (int) user.getAttributes().get("id");

        String username = (String) user.getAttributes().get("name");
        String sub = (String) user.getAttributes().get("sub");
        String picture = (String) user.getAttributes().get("picture");
        String email = (String) user.getAttributes().get("email");
        String role = "ROLE_USER";

//        resultMap.put("id", id);
        resultMap.put("username", username);
        resultMap.put("sub", sub);
        resultMap.put("picture", picture);
        resultMap.put("email", email);
        resultMap.put("role", role);
        System.out.println("username = " + username);

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @GetMapping("/naver")
    public ResponseEntity<Map<String, Object>> getNaverUser(@AuthenticationPrincipal OAuth2User user) {

        System.out.println("user naver 2@@@@@@@@@@2 = " + user);
        HttpStatus status = HttpStatus.ACCEPTED;
        Map<String, Object> resultMap = new HashMap<String, Object>();

        Map<String, Object> attribute = (Map<String, Object>) user.getAttributes();
        Map<String, Object> response = (Map<String, Object>) attribute.get("response");

        String username = (String) response.get("name");
        String sub = (String) response.get("id");
        String picture = (String) response.get("profile_image");
        String email = (String) response.get("email");
        String role = "ROLE_USER";

        resultMap.put("username", username);
        resultMap.put("sub", sub);
        resultMap.put("picture", picture);
        resultMap.put("email", email);
        resultMap.put("role", role);

        // @AuthenticationPrincipal 어노테이션을 사용하여 현재 인증된 사용자 정보를 받아올 수 있습니다.
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }
}
