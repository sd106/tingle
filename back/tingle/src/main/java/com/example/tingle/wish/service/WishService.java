package com.example.tingle.wish.service;

import com.example.tingle.wish.dto.WishDto;
import com.example.tingle.wish.dto.request.WishRequest;

import java.util.List;

public interface WishService {

    // 해당 스타의 위시 조회 (신규순, 추천순, 미션금순)
    public List<WishDto> readWishes(Long starId, int sorting, int status);

    // 해당 위시 등록
    public void saveWish(WishRequest wishRequest);

    // 해당 위시 수정
    public void updateWish(WishRequest wishRequest);

    // 해당 위시 삭제
    public void deleteWish(Long wishId, Long starId, Long userId);

    // 해당 위시에 미션금 추가
    public void addPoints(Long wishId, Long userId, int points);

    /* star */
    // 해당 위시를 미채택/채택 상태로 변경 (스타)
    public void updateWishStatusByStarId(Long starId, Long wishId, int wishStatus);

    /* user */
    // 해당 위시를 완료 상태로 변경 (팬)
    public void updateWishStatusByUserId(Long userId, Long wishId);
}
