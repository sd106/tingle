package com.example.tingle.star.service;

import com.example.tingle.star.entity.StarEntity;

import java.util.List;

public interface StarService {
    StarEntity findByUsername(String username);

    List<StarEntity> findStarsByCategory(int category);
}
