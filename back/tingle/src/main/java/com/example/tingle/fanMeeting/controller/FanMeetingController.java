package com.example.tingle.fanMeeting.controller;

import com.example.tingle.fanMeeting.service.FanMeetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FanMeetingController {
    private final FanMeetingService fanMeetingService;

}
