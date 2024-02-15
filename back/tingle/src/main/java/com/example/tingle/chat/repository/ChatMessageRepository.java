package com.example.tingle.chat.repository;

import com.example.tingle.chat.dto.ChatRoomDto;
import com.example.tingle.chat.entity.ChatMessageEntity;
import com.example.tingle.chat.entity.ChatRoomEntity;
import com.example.tingle.wish.dto.response.Response;
import com.example.tingle.wish.entity.LikesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Long> {
    // 메시지 목록 조회 (방 id)
    @Query("SELECT cm FROM ChatMessageEntity cm WHERE cm.chatRoom.id = :roomId ORDER BY cm.createdDate ASC")
    List<ChatMessageEntity> findMessagesByChatRoom(Long roomId);

    // 메시지 목록 마지막 1개 조회
    @Query("SELECT cm FROM ChatMessageEntity cm WHERE cm.chatRoom.id = :roomId ORDER BY cm.createdDate ASC limit 1")
    ChatMessageEntity findTheMessageByChatRoom(Long roomId);

//    // 메시지 검색 조회
//    @Query("SELECT cm FROM ChatMessageEntity cm WHERE cm.id = :starId AND cm.message = :message ORDER BY cm.createdDate DESC") `
//    List<ChatMessageEntity> findAllByMessageContaining(Long starId, String message);

    // 해당 스타의 모든 메시지 삭제
    @Query("DELETE FROM ChatMessageEntity cm WHERE cm.chatRoom.id = :roomId")
    void deleteMessagesByChatRoom(Long roomId);
}
