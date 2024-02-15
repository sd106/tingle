package com.example.tingle.fanMeeting.dto.response;

import com.example.tingle.fanMeeting.dto.request.CreateFanMeetingRequest;
import com.example.tingle.fanMeeting.entity.FanMeetingType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetFanMeetingInfoResponse {

        private Long id;
        private String status;
        private String title;
        private String description;
        private String fanMeetingStartAt;
        private String ticketingStartAt;
        private String ticketingEndAt;
        private Integer capacity;
        private Integer price;
        private String imgURL1;
        private String imgURL2;
        private List<FanMeetingType> availableTypes;

}
