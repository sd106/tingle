package com.example.tingle.chat.dto;

import com.example.tingle.chat.entity.ChatRoomEntity;
import com.example.tingle.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
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
