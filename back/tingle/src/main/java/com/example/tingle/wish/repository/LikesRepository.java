package com.example.tingle.wish.repository;

import com.example.tingle.wish.entity.LikesEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikesRepository extends JpaRepository<LikesEntity, Long> {
    /**** 공통 ****/
    // 해당 위시의 추천 상태 토글 변경
    @Modifying
    @Transactional
    @Query("UPDATE LikesEntity l SET l.liked = CASE WHEN l.liked = TRUE THEN FALSE ELSE TRUE END WHERE l.wish.id = :wishId")
    void updateLikedByWishId(Long wishId);

    /**** 팬 ****/
    // 해당 스타 위시에 대한 내 추천 목록 조회 (팬)
    @Query("SELECT l FROM LikesEntity l WHERE l.user.id = :userId")
    List<LikesEntity> findAllByUserId(Long userId);

    // 해당 위시에 대한 나의 추천 여부 조회 (팬)
    @Query("SELECT l FROM LikesEntity l WHERE l.wish.id = :wishId AND l.user.id = :userId")
    LikesEntity findByIds(Long wishId, Long userId);

    /**** 일단은 스타는 위시 아이템을 추천할 수 없음 ****/
//    /**** 스타 ****/
//    // 해당 스타 위시에 대한 내 추천 목록 조회 (스타)
//    @Query("SELECT l FROM LikesEntity l WHERE l.star.id = :userId")
//    List<LikesEntity> findAllByStarId(Long userId);
//
//    // 해당 위시에 대한 나의 추천 여부 조회 (스타)
//    @Query("SELECT l FROM LikesEntity l WHERE l.wish.id = :wishId AND l.star.id = :userId")
//    LikesEntity findByStarIds(Long wishId, Long starId);
}