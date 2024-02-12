package com.example.tingle.home.repository;

import com.example.tingle.home.entity.HomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeRepository extends JpaRepository<HomeEntity, Long> {
}
