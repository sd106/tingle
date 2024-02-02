package com.example.tingle.wish.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LikesRequest {
    private Long id;
    private Long userId;
    private Long wishId;
    private boolean liked;
}