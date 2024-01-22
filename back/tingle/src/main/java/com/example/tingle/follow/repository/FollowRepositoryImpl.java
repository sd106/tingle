package com.example.tingle.follow.repository;

import com.example.tingle.follow.dto.response.FollowResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.tingle.follow.entity.QFollow.follow;

@RequiredArgsConstructor
public class FollowRepositoryImpl implements CustomFollowRepository {

    private final JPAQueryFactory query;


    @Override
    public List<String> getFollows(int userId) {
        return query.select(follow.starEntity.nickName)
                .from(follow)
                .join(follow.starEntity)
                .where(follow.id.eq(userId))
                .fetch();
    }
}
