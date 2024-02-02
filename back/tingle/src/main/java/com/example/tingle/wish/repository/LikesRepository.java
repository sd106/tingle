package com.example.tingle.wish.repository;

import com.example.tingle.wish.entity.LikesEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesRepository extends JpaRepository<LikesEntity, Long> {
    // 해당 위시에 대한 나의 추천 여부 조회
    @Query("SELECT l FROM LikesEntity l WHERE l.wish.id = :wishId AND l.user.id = :userId")
    LikesEntity findByWishId(Long wishId, Long userId);

    // 해당 위시의 추천 상태 토글 변경
    @Modifying
    @Transactional
    @Query("UPDATE LikesEntity l SET l.liked = CASE WHEN l.liked = TRUE THEN FALSE ELSE TRUE END")
    void updateLikedByWishId(Long wishId);
}