package com.example.tingle.wish.repository;

import com.example.tingle.wish.entity.WishEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishRepository extends JpaRepository<WishEntity, Long> {

    // 해당 스타에 대한 모든 위시 조회
    @Query("SELECT w FROM WishEntity w WHERE w.star.id = :starId")
    List<WishEntity> findByStarId(@Param("starId") Long starId);
}