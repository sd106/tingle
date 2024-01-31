package com.example.tingle.wish.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LikesRequest {
    private Long id; // 추천 아이디
    private int userId; // 유저 아이디
    private Long wishId; // 위시 아이디
}