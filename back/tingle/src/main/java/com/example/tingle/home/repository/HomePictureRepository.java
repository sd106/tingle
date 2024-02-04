package com.example.tingle.home.repository;

import com.example.tingle.home.entity.HomePictureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomePictureRepository extends JpaRepository<HomePictureEntity, Long> {
}
