package com.example.tingle.wish.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LikesDto {
    private Long id;
    private Long userId;
    private Long wishId;
    private boolean liked;
}
