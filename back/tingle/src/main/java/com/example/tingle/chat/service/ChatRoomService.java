package com.example.tingle.chat.service;

import com.example.tingle.chat.dto.ChatEnterDto;
import com.example.tingle.chat.dto.ChatRoomDto;
import com.example.tingle.chat.dto.ChatUserDto;

import java.util.List;

public interface ChatRoomService {

    // 구독 & 추가한 스타들의 채팅방 조회
    public List<ChatRoomDto> findRoomsByUserId(Long userId);

    // 해당 채팅방 조회 (스타)
    public ChatRoomDto findTheRoomByStarId(Long starId);

    // 채팅방 유저 정보 조회
    public List<ChatUserDto> findChatRoomsUserInfoByUserId(Long userId);

    // 해당 스타의 채팅방 생성 (스타 가입 시)
    public void saveTheRoomByStarEmail(String starEmail);

    // 해당 스타의 채팅방 삭제 (스타 탈퇴 시)
    public void deleteByStarId(Long starId);

    // 해당 스타의 채팅방 삭제 (팬)
    public void ExitByUserId(Long starId, Long userId);

    // 해당 스타의 채팅방 추가 (팬)
    public void EnterByUserId(Long starId, Long userId);

    // 해당 스타의 프로필 이미지 조회

}
