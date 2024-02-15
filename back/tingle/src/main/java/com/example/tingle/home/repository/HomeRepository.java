package com.example.tingle.home.repository;

import com.example.tingle.home.entity.HomeEntity;
import com.example.tingle.home.entity.QHomeEntity;
import com.querydsl.core.QueryResults;
import com.querydsl.core.annotations.QueryProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HomeRepository extends JpaRepository<HomeEntity, Long>, CustomHomeRepository {

}
