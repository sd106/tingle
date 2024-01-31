package com.example.tingle.fanMeeting.repository;

import com.example.tingle.fanMeeting.entity.FanMeeting;
import com.example.tingle.user.entity.StarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FanMeetingRepository extends JpaRepository<FanMeeting, Long>, CustomFanMeetingRepository {
    FanMeeting findRecentFanMeetingByStar(StarEntity star);
}