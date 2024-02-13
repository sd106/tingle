package com.example.tingle.chat.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatEnterDto {
    private Long id; // 관계 아이디
    private Long followId; // 팔로우 아이디
    private Long roomId; //  아이디
}
