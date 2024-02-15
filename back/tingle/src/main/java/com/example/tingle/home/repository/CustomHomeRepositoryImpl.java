package com.example.tingle.home.repository;

import com.example.tingle.home.entity.HomeEntity;
import com.example.tingle.home.entity.QHomeEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.tingle.home.entity.QHomeEntity.homeEntity;

@Repository
@RequiredArgsConstructor
public class CustomHomeRepositoryImpl implements CustomHomeRepository{

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<HomeEntity> findByStarIdOrderByOrdering(Long starId) {

        return (List<HomeEntity>) jpaQueryFactory
                .from(homeEntity)
                .where(homeEntity.starEntity.id.eq(starId))
                .orderBy(homeEntity.ordering.asc())
                .fetch();

    }
}


