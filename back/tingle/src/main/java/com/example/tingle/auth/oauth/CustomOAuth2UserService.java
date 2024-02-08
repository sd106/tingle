package com.example.tingle.auth.oauth;

import com.example.tingle.auth.dto.CustomOAuth2User;
import com.example.tingle.auth.entity.Member;
import com.example.tingle.auth.oauth.provider.GoogleUserInfo;
import com.example.tingle.auth.oauth.provider.NaverUserInfo;
import com.example.tingle.auth.oauth.provider.OAuth2UserInfo;
import com.example.tingle.auth.repository.MemberRepository;
import com.example.tingle.user.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final HttpSession httpSession;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 구글로부터 받은 userRequest 데이터에 대한 후처리 함수
     * UserRequest : ClientRegistration, AccessToken, Attributes
     * 액세서 토큰으로 받아온 사용자 정보가 Request안에 들어있다
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String socialType = userRequest.getClientRegistration().getRegistrationId();
        System.out.println("socialType = " + socialType);

        OAuth2UserInfo oAuth2UserInfo = null;
        if (socialType.equals("google")) {
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else if (socialType.equals("naver")) {
            oAuth2UserInfo = new NaverUserInfo((Map<String, Object>) oAuth2User.getAttributes().get("response"));
        }
        System.out.println("oAuth2UserInfo = " + oAuth2UserInfo);
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName(); // OAuth2 로그인 시 키(PK)가 되는 값

        Map<String, Object> attributes = oAuth2User.getAttributes(); // 소셜 로그인에서 API가 제공하는 userInfo의 Json 값(유저 정보들)


        // socialType에 따라 유저 정보를 통해 OAuthAttributes 객체 생성
        OAuthAttribute extractAttribute = OAuthAttribute.of(socialType, userNameAttributeName, attributes);

        Member createdMember = getMember(extractAttribute, socialType, oAuth2UserInfo); // getUser() 메소드로 User 객체 생성 후 반환

        // 추가 정보를 받아서 정회원이 되었는지
        Boolean isRealMember = createdMember.getIsRealMember();
        
        if (socialType.equals("naver")) {
            attributes = (Map<String, Object>) attributes.get("response");
        }

        // DefaultOAuth2User를 구현한 CustomOAuth2User 객체를 생성해서 반환
        System.out.println(oAuth2User.getAuthorities());
        System.out.println(attributes);
        System.out.println(extractAttribute.getNameAttributeKey());
        System.out.println(createdMember.getEmail());
        System.out.println(isRealMember);
        return new CustomOAuth2User(
                oAuth2User.getAuthorities(),
                attributes,
                extractAttribute.getNameAttributeKey(),
                createdMember.getEmail(),
                isRealMember
        );
    }

    private Member getMember(OAuthAttribute attribute, String socialType, OAuth2UserInfo oAuth2UserInfo){
        Member member = memberRepository.findBySocialIdAndSocialType(oAuth2UserInfo.getProviderId(),socialType).orElse(null);
        if (member == null) {
            Member createdMemeber = attribute.toEntity(oAuth2UserInfo.getEmail(), oAuth2UserInfo.getName(), socialType, oAuth2UserInfo.getProviderId());
            return memberRepository.save(createdMemeber);
        }
        return member;
    }
}

/**
 * 소셜 로그인으로 가져온 사용자 정보 활용하는 코드
 * registrationId: 현재 로그인 중인 서비스(구글, 네이버 등)를 구분한다.
 * userNameAttributeName: OAuth 2 로그인 진행 시 기준이 되는 필드값이다. (PK와 유사하다.) 구글의 기본 코드는 "sub"이며, 네이버나 카카오 등은 기본 지원하지 않는다.
 * SessitonUser: 세션에 사용자 정보를 저장하기 위한 Dto 클래스이다.
 */