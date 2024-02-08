package com.example.tingle.fanMeeting.controller;

import com.example.tingle.fanMeeting.dto.request.CreateFanMeetingRequest;
import com.example.tingle.fanMeeting.dto.response.GetFanMeetingInfoResponse;
import com.example.tingle.fanMeeting.entity.FanMeeting;
import com.example.tingle.fanMeeting.entity.FanMeetingReservation;
import com.example.tingle.fanMeeting.entity.FanMeetingType;
import com.example.tingle.fanMeeting.service.FanMeetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fanMeeting")
@RequiredArgsConstructor
public class FanMeetingController {
    private final FanMeetingService fanMeetingService;

    @GetMapping("/types")
    public List<FanMeetingType> getFanMeetingTypes() {
        System.out.println("getFanMeetingTypes");
        return fanMeetingService.getFanMeetingTypes();

    }

    @PostMapping("/")
    public FanMeeting createFanMeeting(@RequestBody CreateFanMeetingRequest request) {
        System.out.println("makeFanMeeting");
        return fanMeetingService.createFanMeeting(request);
    }

    @GetMapping("/info/{starId}")
    public GetFanMeetingInfoResponse getFanMeetingInfo(@PathVariable Long starId) {
        System.out.println("getFanMeetingInfo");
        return fanMeetingService.getFanMeetingInfo(starId);
    }

    @GetMapping("/reservation/{fanId}")
    public FanMeetingReservation getFanMeetingReservation(@PathVariable Long fanId) {
        System.out.println("getFanMeetingReservation");
        return fanMeetingService.getFanMeetingReservation(fanId);
    }

}