package com.example.tingle.fanMeeting.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FanMeeting {
    @Id
    private Long id;
    private String title;
    private Integer capacity;
    private String description;
    private Integer price;

    private LocalDateTime ticketingStartAt;
    private LocalDateTime ticketingEndAt;
    private LocalDateTime fanMeetingStartAt;

    @OneToOne
    private FanMeetingType fanMeetingType;
}
