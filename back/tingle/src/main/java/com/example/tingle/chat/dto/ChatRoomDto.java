package com.example.tingle.chat.dto;

import com.example.tingle.chat.entity.ChatMessageEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ChatRoomDto {
    private Long id;
    private Long starId;
    private List<ChatMessageDto> chatMessage;
}
