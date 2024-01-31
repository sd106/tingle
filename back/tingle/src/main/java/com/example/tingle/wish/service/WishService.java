package com.example.tingle.wish.service;

import com.example.tingle.wish.dto.request.WishRequest;
import com.example.tingle.wish.dto.response.WishResponse;

import java.util.List;

public interface WishService {

    // 해당 스타의 위시 조회
    public List<WishResponse> read(Long starId);

    // 해당 위시 등록
    public void saveWish(WishRequest wishRequest);

    // 해당 위시 수정
    public void updateWish(WishRequest wishRequest);

    // 해당 위시 삭제
    public void deleteWish(Long id);

    // 해당 위시에 미션금 추가

    // 해당 위시에 미션금 취소
}
