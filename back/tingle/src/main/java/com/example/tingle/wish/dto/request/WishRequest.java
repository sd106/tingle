package com.example.tingle.wish.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class WishRequest {
    private Long id;
    private Long userId;
    private Long starId;
    private int status;
    private int points;
    private int likedCount;
    private String contents;
    private LocalDate createTime;
    private LocalDate deleteTime;
}
