package com.example.tingle.fanMeeting.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class FanMeetingReservationMessageDto {
    private LocalDateTime orderAt;
    private String userName;
    private String starName;
    private String fanMeetingType;
}
