package com.example.tingle.fanMeeting.controller;

import com.example.tingle.fanMeeting.dto.request.CreateFanMeetingRoomRequest;
import com.example.tingle.fanMeeting.service.FanMeetingRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fanMeetingRoom")
@RequiredArgsConstructor
public class FanMeetingRoomController {
    private final FanMeetingRoomService fanMeetingRoomService;

    @PostMapping("/create")
    public void createFanMeetingRoom(@RequestBody CreateFanMeetingRoomRequest request) {
        fanMeetingRoomService.createRoom(request);

    }
    public void joinWaitingRoom() {

    }

    public void joinMeetingRoom() {

    }

}
