package com.example.tingle.fanMeeting.repository;

import com.example.tingle.fanMeeting.entity.FanMeetingReservation;

public interface CustomFanMeetingReservationRepository {
    FanMeetingReservation findRecentFanMeetingByUserAndStar(Long userId, Long starId);
}
