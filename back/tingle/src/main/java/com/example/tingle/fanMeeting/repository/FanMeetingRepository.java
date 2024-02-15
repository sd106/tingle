package com.example.tingle.fanMeeting.repository;

import com.example.tingle.fanMeeting.entity.FanMeeting;
import com.example.tingle.star.entity.StarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FanMeetingRepository extends JpaRepository<FanMeeting, Long>, CustomFanMeetingRepository {
    FanMeeting findRecentFanMeetingByStar(StarEntity star);

    Optional<FanMeeting> findById(Long id);
}
