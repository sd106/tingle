package com.example.tingle.wish.repository;

import com.example.tingle.wish.entity.WishEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishRepository extends JpaRepository<WishEntity, Long> {
    // 해당 스타에 대한 모든 위시 추천 높은 순으로 조회 (정렬)
    @Query("SELECT w FROM WishEntity w WHERE w.star.id = :starId ORDER BY w.likedCount DESC")
    List<WishEntity> findByStarIdWithLikes(Long starId);

    // 해당 스타에 대한 모든 위시 미션금 높은 순으로 조회 (정렬)
    @Query("SELECT w FROM WishEntity w WHERE w.star.id = :starId ORDER BY w.points DESC")
    List<WishEntity> findByStarIdWithPoints(Long starId);

    // 해당 위시의 상태 변경 (0: Not Started, 1: In Progress, 2: Done)
    @Modifying
    @Transactional
    @Query("UPDATE WishEntity w SET w.status = :status WHERE w.id = :wishId")
    void updateStatusByWishId(Long wishId, int status);

    // 해당 위시의 미션금 추가 (결제 기능 확립 시 포인트 사용 내역 Entity 추가 예정)
    @Modifying
    @Transactional
    @Query("UPDATE WishEntity w SET w.points = :points + w.points WHERE w.id = :wishId")
    void updatePointsByWishId(Long wishId, int points);

    // 해당 위시의 liked 업데이트 반영 (+)
    @Modifying
    @Transactional
    @Query("UPDATE WishEntity w SET w.likedCount = w.likedCount+1 WHERE w.id = :wishId")
    void updateLikedPlusByWishId(Long wishId);

    // 해당 위시의 liked 업데이트 반영 (-)
    @Modifying
    @Transactional
    @Query("UPDATE WishEntity w SET w.likedCount = w.likedCount-1 WHERE w.id = :wishId")
    void updateLikedMinusByWishId(Long wishId);
}