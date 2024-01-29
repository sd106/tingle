package com.example.tingle.fanMeeting.utils;

import com.example.tingle.fanMeeting.model.FanMeetingRoom;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class MeetingRoomMap {
    private static final MeetingRoomMap meetingRoomMap = new MeetingRoomMap();
    private Map<Long, FanMeetingRoom> meetingRooms = new LinkedHashMap<>();

    private MeetingRoomMap(){}

    public static MeetingRoomMap getInstance(){
        return meetingRoomMap;
    }


}