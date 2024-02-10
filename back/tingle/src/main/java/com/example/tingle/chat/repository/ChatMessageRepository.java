package com.example.tingle.chat.repository;

import com.example.tingle.chat.entity.ChatMessageEntity;
import com.example.tingle.chat.entity.ChatRoomEntity;
import com.example.tingle.wish.entity.LikesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Long> {
    // 메시지 목록 조회
    @Query("SELECT cm FROM ChatMessageEntity cm WHERE cm.chatRoom.star.id = :starId ORDER BY cm.createdDate DESC")
    List<ChatMessageEntity> findMessagesByChatRoom(Long starId);

//    // 메시지 검색 조회
//    @Query("SELECT cm FROM ChatMessageEntity cm WHERE cm.id = :starId AND cm.message = :message ORDER BY cm.createdDate DESC") `
//    List<ChatMessageEntity> findAllByMessageContaining(Long starId, String message);

    // 메시지 삭제
    @Query("DELETE FROM ChatMessageEntity cm WHERE cm.chatRoom.star.id = :starId")
    void deleteMessagesByChatRoom(Long starId);
}
