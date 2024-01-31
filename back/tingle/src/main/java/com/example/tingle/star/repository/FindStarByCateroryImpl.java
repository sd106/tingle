package com.example.tingle.star.repository;

import com.example.tingle.star.dto.response.ReadStarByCategory;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.tingle.follow.entity.QFollowEntity.followEntity;
import static com.example.tingle.star.entity.QStarEntity.starEntity;


@Repository
@RequiredArgsConstructor
public class FindStarByCateroryImpl implements FindStarByCaterory{


    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ReadStarByCategory> findStarsByCategory(int category) {

        return jpaQueryFactory
                .select(
                        Projections.constructor(ReadStarByCategory.class,
                                starEntity.id,
                                starEntity.username,
                                starEntity.picture
                                )
                )
                .from(starEntity)
                .innerJoin(starEntity.followerUsers, followEntity)
                .on(followEntity.starEntity.eq(starEntity))
                .where(starEntity.category.eq(category))
                .groupBy(starEntity.id)
                .orderBy(starEntity.followerUsers.size().desc())
                .limit(2)
                .fetch();
    }

    }