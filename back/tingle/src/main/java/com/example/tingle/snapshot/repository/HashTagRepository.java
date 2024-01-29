package com.example.tingle.snapshot.repository;

import com.example.tingle.snapshot.entity.HashTagEntity;
import com.example.tingle.snapshot.entity.SnapShotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashTagRepository extends JpaRepository<HashTagEntity, Long> {

    boolean existsByTag(String tag);

    HashTagEntity findByTag(String tag);
}
