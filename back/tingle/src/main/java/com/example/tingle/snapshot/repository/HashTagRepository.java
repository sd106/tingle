package com.example.tingle.snapshot.repository;

import com.example.tingle.snapshot.entity.HashTagEntity;
import com.example.tingle.snapshot.entity.SnapShotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HashTagRepository extends JpaRepository<HashTagEntity, Long> {

    boolean existsByTag(String tag);

    Optional<HashTagEntity> findByTag(String tag);
}
