package com.example.tingle.fanMeeting.entity;

import com.example.tingle.user.entity.StarEntity;
import com.example.tingle.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class FanMeeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Integer capacity;
    private Integer price;
    private LocalDateTime ticketingStartAt;
    private LocalDateTime ticketingEndAt;
    private LocalDateTime fanMeetingStartAt;

    @OneToMany
    private List<FanMeetingType> availableFanMeetingTypes;

    @OneToOne
    private UserEntity fan;
    @OneToOne
    private StarEntity star;
}
