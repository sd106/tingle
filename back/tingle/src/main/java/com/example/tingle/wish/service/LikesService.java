package com.example.tingle.wish.service;

import com.example.tingle.wish.dto.LikesDto;

import java.util.List;

public interface LikesService {
    // 해당 스타 위시에 대한 내 추천 목록 조회
    public List<LikesDto> readLikesList(Long userId);

    // 해당 위시에 대한 추천 여부 조회
    public int isLikes(Long wishId, Long userId);

    // 해당 위시에 대한 추천 상태 변경
    public int updateLikes(Long wishId, Long userId);
}
