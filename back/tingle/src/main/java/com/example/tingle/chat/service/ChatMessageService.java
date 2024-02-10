package com.example.tingle.chat.service;

import com.example.tingle.chat.dto.ChatMessageDto;

import java.util.List;

public interface ChatMessageService {
    // 해당 스타 유저들의 채팅 메시지 조회
    public List<ChatMessageDto> findMessagesByStarId(Long starId);

    // 메시지 저장
    public void saveMessages(ChatMessageDto chatMessageDto);

    // 일정 시간 이전의 메시지 삭제
    public void deleteMessages(Long starId);
}