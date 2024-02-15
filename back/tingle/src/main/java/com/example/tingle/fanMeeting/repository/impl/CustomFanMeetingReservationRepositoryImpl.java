package com.example.tingle.fanMeeting.repository.impl;

import com.example.tingle.fanMeeting.entity.FanMeetingReservation;
import com.example.tingle.fanMeeting.entity.QFanMeetingReservation;
import com.example.tingle.fanMeeting.repository.CustomFanMeetingReservationRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomFanMeetingReservationRepositoryImpl implements CustomFanMeetingReservationRepository {
    private final JPAQueryFactory queryFactory;
    @Override
    public FanMeetingReservation findRecentFanMeetingByUserAndStar(Long userId, Long starId) {
        return queryFactory
                .selectFrom(QFanMeetingReservation.fanMeetingReservation)
                .where(QFanMeetingReservation.fanMeetingReservation.user.id.eq(userId)
                        .and(QFanMeetingReservation.fanMeetingReservation.star.id.eq(starId)))
                .orderBy(QFanMeetingReservation.fanMeetingReservation.orderAt.desc())
                .fetchFirst();
    }
}
