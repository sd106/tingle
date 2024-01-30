package com.example.tingle.star.service;

import com.example.tingle.star.dto.response.ReadStarByCategory;
import com.example.tingle.star.entity.StarEntity;

import java.util.List;

public interface StarService {
    StarEntity findByUsername(String username);

    List<ReadStarByCategory> findStarsByCategory(int category);
}
