package com.example.tingle.wish.repository;

import com.example.tingle.wish.entity.LikesEntity;
import com.example.tingle.wish.entity.WishEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikesRepository extends JpaRepository<LikesEntity, Long> {
//    // 해당 위시에 대한 나의 좋아요 여부 조회
//    @Query("SELECT l FROM LikesEntity l WHERE l.id = :wishId AND l.user.id = :userId")
//    LikesEntity findByWishId(Long wishId, int userId);
//
//    // 해당 위시에 대한 모든 좋아요 수 조회
//    @Query("SELECT count(*) FROM LikesEntity l WHERE l.id = :wishId")
//    int getLikesCountByWishId(Long wishId);
//
//    // 해당 위시에 대한 추천 상태 변경
//    @Modifying
//    @Query("UPDATE LikesEntity u SET u.name = :newName WHERE u.id = :userId")
//    void updateStatus(Long userId);
//
//    @Query("UPDATE FROM LikesEntity l SET l.id = :wishId")
//    boolean updateById(Long wishId, int userId);
}