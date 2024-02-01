package com.example.tingle.wish.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class WishDto {
    private Long id;
    private int userId;
    private Long starId;
    private int status;
    private int points;
    private int likedCount;
    private String contents;
    private LocalDate createTime;
    private LocalDate deleteTime;
}