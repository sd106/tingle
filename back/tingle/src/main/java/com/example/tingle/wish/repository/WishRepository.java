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
    /**** 공통 ****/
    // 해당 스타에 대한 모든 위시 최신순 조회 (정렬, 미채택/채택/완료)
    @Query("SELECT w FROM WishEntity w WHERE w.star.id = :starId AND w.status = :status ORDER BY w.id DESC")
    List<WishEntity> findByStarIdWithNews(Long starId, int status);

    // 해당 스타에 대한 모든 위시 추천순 조회 (정렬, 미채택/채택/완료)
    @Query("SELECT w FROM WishEntity w WHERE w.star.id = :starId AND w.status = :status ORDER BY w.likedCount DESC")
    List<WishEntity> findByStarIdWithLikes(Long starId, int status);

    // 해당 스타에 대한 모든 위시 미션금순 조회 (정렬, 미채택/채택/완료)
    @Query("SELECT w FROM WishEntity w WHERE w.star.id = :starId AND w.status = :status ORDER BY w.points DESC")
    List<WishEntity> findByStarIdWithPoints(Long starId, int status);

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

    /**** 스타 ****/
    // 해당 위시의 상태 변경 (0: Not Started, 1: In Progress)
    @Modifying
    @Transactional
    @Query("UPDATE WishEntity w SET w.status = :status WHERE w.id = :wishId AND w.star.id = :starId")
    void updateStatusByStarId(Long starId, Long wishId, int status);

    /**** 팬 ****/
    // 해당 위시의 상태 변경 (2: Done)
    @Modifying
    @Transactional
    @Query("UPDATE WishEntity w SET w.status = :status WHERE w.id = :wishId AND w.user.id = :userId")
    void updateStatusByUserId(Long userId, Long wishId);

    // 해당 위시의 미션금 추가 (결제 기능 확립 시 포인트 사용 내역 Entity 추가 예정)
    @Modifying
    @Transactional
    @Query("UPDATE WishEntity w SET w.points = :points + w.points WHERE w.id = :wishId")
    void updatePointsByWishId(Long wishId, int points);
}