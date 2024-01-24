package com.example.tingle.snapshot.repository;

import com.example.tingle.snapshot.entity.SnapShotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SnapShotRepository extends JpaRepository<SnapShotEntity, Long> {

    // 추천수별 정렬
    List<SnapShotEntity> findAllByLikesOrderByLikesDesc(SnapShotEntity snapShot);

    // 최신순 정렬
    List<SnapShotEntity> findAllByCreatedTimeOrderByCreatedTimeDesc(SnapShotEntity snapShot);

}
