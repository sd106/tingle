package com.example.tingle.auth.oauth;

import com.example.tingle.auth.oauth.provider.GoogleUserInfo;
import com.example.tingle.auth.oauth.provider.NaverUserInfo;
import com.example.tingle.auth.oauth.provider.OAuth2UserInfo;
import com.example.tingle.auth.dto.CustomUserDetails;
import com.example.tingle.user.entity.Role;
import com.example.tingle.user.entity.UserEntity;
import com.example.tingle.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    /**
     * 구글로부터 받은 userRequest 데이터에 대한 후처리 함수
     * UserRequest : ClientRegistration, AccessToken, Attributes
     * 액세서 토큰으로 받아온 사용자 정보가 Request안에 들어있다
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        System.out.println("delegate = " + delegate);
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        System.out.println("oAuth2User = " + oAuth2User);
        System.out.println("userRequest = " + userRequest.getClientRegistration()); // 어떤 OAuth로 로그인 했는지 알 수 있다
        System.out.println("getAccessToken = " + userRequest.getAccessToken().getTokenValue());
        // 구글 로그인 버튼 -> 로그인창 -> 완료 -> code 리턴 -> AccessToken 요청
        // UserRequest 정보 -> 회원 프로필 받아야함(loadUser 함수)
        System.out.println("getAttribute = " + oAuth2User.getAttributes());

        OAuth2UserInfo oAuth2UserInfo = null;
        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            System.out.println("구글 로그인 요청");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
            oAuth2UserInfo = new NaverUserInfo((Map<String, Object>) oAuth2User.getAttributes().get("response"));
        }

        String provider = oAuth2UserInfo.getProvider(); // google
        String providerId = oAuth2UserInfo.getProviderId();
        String username = provider + "_" + providerId;
//        String password = bCryptPasswordEncoder.encode("qweasdadgehethf");
        String email = oAuth2UserInfo.getEmail();
        String picture = oAuth2UserInfo.getPicture();

        UserEntity userEntity = userRepository.findByUsername(username);

        if (userEntity == null) {
            System.out.println("OAuth 로그인이 최초입니다");
            userEntity = UserEntity.builder()
                    .username(username)
                    .password("qwtdnghjjsdffhghkghjfafssfjghkgasfshjfghhhgraggethrhwwytmthdsvafdgxvssdsb")
                    .email(email)
                    .role(Role.FAN)
                    .provider(provider)
                    .picture(picture)
                    .build();
            userRepository.save(userEntity);
        } else {
            System.out.println("이미 OAuth 로그인 한 적이 있습니다.");
        }
        // Authentication 안에 들어감
        return new CustomUserDetails(userEntity, oAuth2User.getAttributes());
    }

    private UserEntity saveOrUpdate(OAuthAttribute attributes) {
        UserEntity user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getUsername(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}

/**
 * 소셜 로그인으로 가져온 사용자 정보 활용하는 코드
 * registrationId: 현재 로그인 중인 서비스(구글, 네이버 등)를 구분한다.
 * userNameAttributeName: OAuth 2 로그인 진행 시 기준이 되는 필드값이다. (PK와 유사하다.) 구글의 기본 코드는 "sub"이며, 네이버나 카카오 등은 기본 지원하지 않는다.
 * SessitonUser: 세션에 사용자 정보를 저장하기 위한 Dto 클래스이다.
 */