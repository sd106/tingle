package com.example.tingle.fanMeeting.entity;

import com.example.tingle.user.entity.StarEntity;
import com.example.tingle.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

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

    @OneToOne
    private UserEntity user;

    @OneToOne
    private StarEntity star;

    @OneToOne
    private FanMeeting fanMeeting;
}
