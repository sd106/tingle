package com.example.tingle.wish.repository;

import com.example.tingle.wish.entity.LikesEntity;
import com.example.tingle.wish.entity.WishEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<LikesEntity, Long> {

}