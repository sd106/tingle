package com.example.tingle.auth.dto;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
public class CustomOAuth2User extends DefaultOAuth2User {
    private String email;
    private Boolean isRealMember;
    public CustomOAuth2User(Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes, String nameAttributeKey, String email, Boolean isRealMember ) {
        super(authorities, attributes, nameAttributeKey);
        this.email = email;
        this.isRealMember = isRealMember;
    }

}
