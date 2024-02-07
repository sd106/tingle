package com.example.tingle.snapshot.repository;

import com.example.tingle.snapshot.entity.LikeEntity;
import com.example.tingle.snapshot.entity.SnapShotEntity;
import com.example.tingle.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {

    Optional<LikeEntity> findByUserAndSnapShot(UserEntity user, SnapShotEntity snapShot);
    long countBySnapShot(SnapShotEntity snapShot);
}
