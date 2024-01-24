package com.example.tingle.fanMeeting.dto.request;

import com.example.tingle.fanMeeting.entity.FanMeetingType;
import com.example.tingle.user.entity.StarEntity;
import com.example.tingle.user.entity.UserEntity;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateFanMeetingRequest {

    private String title;
    private String description;
    private String fanMeetingStartAt;
    private String ticketingStartAt;
    private String ticketingEndAt;
    private Integer price;
    private Integer capacity;
    private List<FanMeetingType> availableFanMeetingTypes;
    private String fanName;
    private String starName;
}
