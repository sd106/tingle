package com.example.tingle.chat.dto;

import com.example.tingle.chat.entity.ChatRoomEntity;
import com.example.tingle.user.entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Builder
@Getter
public class ChatMessageDto {
    private Long id;
    private Long userId;
    private int direction;
    private String message;
    private LocalDateTime createdDate;
    private Long roomId;
}
