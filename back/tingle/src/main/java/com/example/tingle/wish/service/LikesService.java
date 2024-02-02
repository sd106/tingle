package com.example.tingle.wish.service;

public interface LikesService {
    // 해당 위시에 대한 추천 여부 조회
    public int isLikes(Long wishId, Long userId);

    // 해당 위시에 대한 추천 상태 변경
    public int updateLikes(Long wishId, Long userId);
}
