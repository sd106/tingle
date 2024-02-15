package com.example.tingle.fanMeeting.repository;

import com.example.tingle.fanMeeting.entity.FanMeeting;
import com.example.tingle.fanMeeting.entity.FanMeetingReservation;
import com.example.tingle.star.entity.StarEntity;
import com.example.tingle.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FanMeetingReservationRepository extends JpaRepository<FanMeetingReservation, Long> {
    List<FanMeetingReservation> findByFanMeeting(FanMeeting fanMeeting);
    Optional<FanMeetingReservation> findByUserAndStarAndFanMeeting(UserEntity user, StarEntity star, FanMeeting fanMeeting);
    FanMeetingReservation findByUser(UserEntity user);
}
