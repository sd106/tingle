package com.example.tingle.wish.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class WishRequest {
    private Long id; // 위시 아이디
    private int userId; // 유저 아이디
    private Long starId; // 스타 아이디
    private boolean status; // 채택 여부
    private int points; // 미션금
    private String contents; // 내용
//    private LocalDate createTime; // 날짜
//    private LocalDate deleteTime; // 날짜
}
