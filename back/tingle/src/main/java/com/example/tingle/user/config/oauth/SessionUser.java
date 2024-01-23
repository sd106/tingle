package com.example.tingle.user.config.oauth;

import com.example.tingle.entity.UserEntity;
import lombok.Getter;

@Getter
public class SessionUser {

    private String username;
    private String email;
    private String picture;

    public SessionUser(UserEntity user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}

/**
 * User 직렬화 코드
 * User 클래스를 그대로 사용하면 직렬화를 구현하지 않았다는 에러가 발생해서 추가
 */