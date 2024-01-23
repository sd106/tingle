package com.example.tingle.fanMeeting.entity;

import com.example.tingle.user.entity.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FanMeetingReservation {
    @Id
    private Long id;

    private LocalDateTime orderAt;

    @OneToOne
    private UserEntity user;

    @OneToOne
    private FanMeeting fanMeeting;
}
