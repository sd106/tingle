package com.example.tingle.snapshot.repository;

import com.example.tingle.snapshot.entity.SnapShotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SnapShotRepository extends JpaRepository<SnapShotEntity, Long> {


}
