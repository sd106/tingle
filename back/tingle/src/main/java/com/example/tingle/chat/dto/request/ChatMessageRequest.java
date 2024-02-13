package com.example.tingle.chat.dto.request;

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

@Getter
@Builder
public class ChatMessageRequest {
    private Long userId;
    private int direction;
    private String message;
    private Long roomId;
}
