package com.example.tingle.fanMeeting.repository;

import com.example.tingle.fanMeeting.entity.FanMeeting;
import com.example.tingle.star.entity.StarEntity;

public interface CustomFanMeetingRepository {
    FanMeeting findRecentFanMeetingByStar(StarEntity star);

}
