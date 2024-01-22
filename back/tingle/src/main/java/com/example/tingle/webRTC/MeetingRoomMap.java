package com.example.tingle.webRTC;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class MeetingRoomMap {
    private static final MeetingRoomMap meetingRoomMap = new MeetingRoomMap();
    private Map<Long, MeetingRoom> meetingRooms = new LinkedHashMap<>();

    private MeetingRoomMap(){}

    public static MeetingRoomMap getInstance(){
        return meetingRoomMap;
    }


}