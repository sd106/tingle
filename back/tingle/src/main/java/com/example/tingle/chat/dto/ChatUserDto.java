package com.example.tingle.chat.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChatUserDto {
    private Long id;
    private String username;
    private String picture;
}
