package com.example.tingle.fanMeeting.entity;

import com.example.tingle.star.entity.StarEntity;
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
    private String imgURL;
    private LocalDateTime ticketingStartAt;
    private LocalDateTime ticketingEndAt;
    private LocalDateTime fanMeetingStartAt;
    private Boolean isFinished;

    @ManyToMany
    private List<FanMeetingType> availableFanMeetingTypes;

    @ManyToOne
    private StarEntity star;
}
