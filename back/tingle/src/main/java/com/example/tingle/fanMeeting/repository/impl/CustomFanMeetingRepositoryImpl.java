package com.example.tingle.fanMeeting.repository.impl;

import com.example.tingle.fanMeeting.entity.FanMeeting;
import com.example.tingle.fanMeeting.entity.QFanMeeting;
import com.example.tingle.fanMeeting.repository.CustomFanMeetingRepository;
import com.example.tingle.star.entity.StarEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomFanMeetingRepositoryImpl implements CustomFanMeetingRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public FanMeeting findRecentFanMeetingByStar(StarEntity star) {
        return queryFactory.selectFrom(QFanMeeting.fanMeeting)
                .where(QFanMeeting.fanMeeting.star.eq(star))
                .orderBy(QFanMeeting.fanMeeting.id.desc())
                .fetchFirst();
    }
}
