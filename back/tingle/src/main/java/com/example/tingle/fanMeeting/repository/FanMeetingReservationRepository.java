package com.example.tingle.fanMeeting.repository;

import com.example.tingle.fanMeeting.entity.FanMeetingReservation;
import com.example.tingle.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FanMeetingReservationRepository extends JpaRepository<FanMeetingReservation, Long> {

    FanMeetingReservation findByUser(UserEntity user);
}
