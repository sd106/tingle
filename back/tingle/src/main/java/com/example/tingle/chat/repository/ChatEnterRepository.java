package com.example.tingle.chat.repository;

import com.example.tingle.chat.entity.ChatEnterEntity;
import com.example.tingle.chat.entity.ChatMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatEnterRepository extends JpaRepository<ChatEnterEntity, Long> {

    /*** 팬 ***/
    // 구독 상태 & 추가한 스타 채팅 방 리스트를 반환
    List<ChatEnterEntity> findEntersByUserId(Long userId);

    // 구독 상태인 스타 추가
    void saveEnter(ChatEnterEntity chatEnterEntity, Long userId);

    // 구독 상태 & 추가한 스타 삭제
    void deleteEnter(Long starId, Long userId);
}