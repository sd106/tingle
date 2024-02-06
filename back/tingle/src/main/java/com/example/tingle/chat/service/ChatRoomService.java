package com.example.tingle.chat.service;

import com.example.tingle.chat.dto.ChatRoomDto;

import java.util.List;

public interface ChatRoomService {

    // 구독한 스타들의 채팅 방 조회
    public List<ChatRoomDto> findRoomsByUserId(Long userId);

    // 해당 채팅 방 조회 (스타)
    public ChatRoomDto findTheRoomByStarId(Long starId);

    // 해당 스타의 채팅 방 생성 (스타 가입 시)
    public void saveTheRoomByStarId(Long starId);

    // 해당 스타의 채팅 방 삭제 (스타 탈퇴 시)
    public void deleteByStarId(Long starId);

    // 해당 스타의 채팅방에서 퇴장 (팬)
    public void ExitByUserId(Long starId, Long userId);

    // 해당 스타의 채팅방으로 입장 (팬)
    public void EnterByUserId(Long starId, Long userId);
}
