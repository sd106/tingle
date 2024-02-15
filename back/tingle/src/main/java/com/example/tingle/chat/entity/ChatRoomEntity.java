package com.example.tingle.chat.entity;

import com.example.tingle.chat.dto.request.ChatRoomRequest;
import com.example.tingle.star.entity.StarEntity;
import com.example.tingle.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="ChatRoom")
public class ChatRoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 방 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "star_id")
    private StarEntity star; // 스타 아이디

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.REMOVE)
    private List<ChatMessageEntity> chatMessageList; // 메시지 리스트
}
