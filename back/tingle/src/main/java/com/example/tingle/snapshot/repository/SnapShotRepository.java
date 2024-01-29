package com.example.tingle.snapshot.repository;

import com.example.tingle.snapshot.entity.SnapShotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SnapShotRepository extends JpaRepository<SnapShotEntity, Long> {

    Optional<SnapShotEntity> findById(Long snapshotId);
    // 추천수별 정렬
    List<SnapShotEntity> findAllByOrderByLikesDesc();

    // 최신순 정렬
    List<SnapShotEntity> findAllByOrderByCreatedTimeDesc();

}
