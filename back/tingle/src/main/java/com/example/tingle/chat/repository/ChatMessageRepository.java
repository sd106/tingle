package com.example.tingle.chat.repository;

import com.example.tingle.chat.entity.ChatMessageEntity;
import com.example.tingle.chat.entity.ChatRoomEntity;
import com.example.tingle.wish.entity.LikesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Long> {
    // 메시지 목록 조회
    List<ChatMessageEntity> findAllByChatRoom(Long starId);

    // 메시지 검색 조회
    List<ChatMessageEntity> findAllByMessageContaining(String message);
}
