package com.example.tingle.chat.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatEnterRequest {
    private Long id; // 관계 아이디
    private Long followId; // 팔로우 아이디
}
