package com.example.tingle.chat.repository;

import com.example.tingle.chat.entity.ChatRoomEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {

    /*** 스타 ***/
    // ChatRoom 조회
    @Query("SELECT cr FROM ChatRoomEntity cr WHERE cr.star.id = :starId ORDER BY cr.star.id DESC")
    ChatRoomEntity findTheRoomByStarId(Long starId);
}
