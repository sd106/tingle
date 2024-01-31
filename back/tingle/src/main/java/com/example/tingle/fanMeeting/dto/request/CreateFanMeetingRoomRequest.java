package com.example.tingle.fanMeeting.dto.request;

import lombok.Getter;

@Getter
public class CreateFanMeetingRoomRequest {
    String roomName;
    Integer maxUserCnt;
    String starName;
}
