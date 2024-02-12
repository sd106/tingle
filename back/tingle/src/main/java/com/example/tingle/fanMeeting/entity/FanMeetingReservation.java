package com.example.tingle.fanMeeting.entity;

import com.example.tingle.star.entity.StarEntity;
import com.example.tingle.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class FanMeetingReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime orderAt;

    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private StarEntity star;

    @ManyToOne
    private FanMeeting fanMeeting;
}
