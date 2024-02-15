package com.example.tingle.home.repository;

import com.example.tingle.home.entity.HomeEntity;

import java.util.List;

public interface CustomHomeRepository {
    List<HomeEntity> findByStarIdOrderByOrdering(Long starId);


}
