package com.example.tingle.chat.service;

import com.example.tingle.chat.dto.ChatMessageDto;
import com.example.tingle.chat.dto.ChatUserDto;
import com.example.tingle.chat.dto.request.ChatMessageRequest;
import com.example.tingle.user.dto.UserDto;

import java.util.List;

public interface ChatMessageService {
    // 해당 스타 유저들의 채팅 메시지 조회
    public List<ChatMessageDto> findMessagesByStarId(Long starId);

    // 해당 스타 유저들의 채팅 메시지당 유저 정보 조회
    public List<ChatUserDto> findMessagesUserInfoByStarId(Long starId);

    // 메시지 저장
    public void saveMessages(ChatMessageRequest chatMessageRequest);

    // 일정 시간 이전의 메시지 삭제
    public void deleteMessages(Long starId);
}