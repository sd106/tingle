package com.example.tingle.chat.repository;

import com.example.tingle.chat.entity.ChatRoomEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {

    // ChatRoom 조회
    ChatRoomEntity findByStarId(Long starId);

    // ChatRoom 목록 조회
    List<ChatRoomEntity> findAllByStarId(Long starId);
}
