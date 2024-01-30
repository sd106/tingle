package com.example.tingle.star.repository;

import com.example.tingle.star.dto.response.ReadStarByCategory;

import java.util.List;

public interface FindStarByCaterory {

    // 카테고리 별 10명 스타조회
    List<ReadStarByCategory> findStarsByCategory(int category);
}
