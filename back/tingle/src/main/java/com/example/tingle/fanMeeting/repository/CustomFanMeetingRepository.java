package com.example.tingle.fanMeeting.repository;

import com.example.tingle.fanMeeting.entity.FanMeeting;
import com.example.tingle.user.entity.StarEntity;

public interface CustomFanMeetingRepository {
    FanMeeting findRecentFanMeetingByStar(StarEntity star);

}
