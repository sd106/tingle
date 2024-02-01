package com.example.tingle.auth.oauth;

import com.example.tingle.user.entity.Role;
import com.example.tingle.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OAuthAttribute {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String username;
    private String email;
    private String picture;

    public  static OAuthAttribute of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if ("naver".equals(registrationId)) {
            return ofNaver("id", attributes);
        }

        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttribute ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttribute.builder()
                .username((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttribute ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttribute.builder()
                .username((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public UserEntity toEntity() {
        return UserEntity.builder()
                .username(username)
                .email(email)
                .picture(picture)
                .role(Role.FAN)
                .build();
    }
}

/**
 * OAuth2User의 attribute를 담을 DTO 구현
 */