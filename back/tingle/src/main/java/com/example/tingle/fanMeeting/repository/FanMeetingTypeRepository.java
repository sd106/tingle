package com.example.tingle.fanMeeting.repository;

import com.example.tingle.fanMeeting.entity.FanMeetingType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FanMeetingTypeRepository extends JpaRepository<FanMeetingType, Long>{
    Optional<FanMeetingType> findById(Long meetingId);
}
