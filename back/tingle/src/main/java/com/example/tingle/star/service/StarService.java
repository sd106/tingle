package com.example.tingle.star.service;

import com.example.tingle.star.dto.request.ReadStarRequest;
import com.example.tingle.star.dto.response.ReadStarByCategory;
import com.example.tingle.star.entity.StarEntity;

import java.util.List;
import java.util.Optional;

public interface StarService {
    StarEntity findByUsername(String username);

    List<StarEntity> findByUsernameContaining(String starname);
    Optional<StarEntity> findById(Long starId);
    List<ReadStarByCategory> findStarsByCategory(int category);

    void save(StarEntity starEntity);
    List<ReadStarRequest> find10Stars();
}
