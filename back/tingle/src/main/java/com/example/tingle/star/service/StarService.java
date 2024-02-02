package com.example.tingle.star.service;

import com.example.tingle.star.dto.request.ReadStarRequest;
import com.example.tingle.star.dto.response.ReadStarByCategory;
import com.example.tingle.star.entity.StarEntity;

import java.util.List;
import java.util.Map;

public interface StarService {
    StarEntity findByUsername(String username);

    List<ReadStarByCategory> findStarsByCategory(int category);

    List<ReadStarRequest> find10Stars();
}
