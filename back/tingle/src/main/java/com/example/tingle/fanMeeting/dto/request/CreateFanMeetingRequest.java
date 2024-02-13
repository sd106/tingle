package com.example.tingle.fanMeeting.dto.request;

import com.example.tingle.fanMeeting.entity.FanMeetingType;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CreateFanMeetingRequest {

    private String title;
    private String description;
    private LocalDateTime fanMeetingStartAt;
    private LocalDateTime ticketingStartAt;
    private LocalDateTime ticketingEndAt;
    private Integer price;
    private Integer capacity;
    private List<FanMeetingType> availableFanMeetingTypes;
    private String starName;
    private String imgURL1;
    private String imgURL2;
}
