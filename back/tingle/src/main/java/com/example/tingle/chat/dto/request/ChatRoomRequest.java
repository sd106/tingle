package com.example.tingle.chat.dto.request;

import com.example.tingle.chat.dto.ChatMessageDto;
import com.example.tingle.chat.entity.ChatMessageEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ChatRoomRequest {
    private Long id;
    private Long starId;
    private List<ChatMessageEntity> chatMessageList;
}
