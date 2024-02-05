package com.example.tingle.star.repository;

import com.example.tingle.star.dto.request.ReadStarRequest;
import com.example.tingle.star.dto.response.ReadStarByCategory;

import java.util.List;

public interface CustomStarRepository {

    // 카테고리 별 10명 스타조회
    List<ReadStarByCategory> findStarsByCategory(int category);

    List<ReadStarRequest> find10Stars();
}
