package com.example.tingle.fanMeeting.model;

import lombok.*;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;

@Data
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FanMeetingRoom {

    private Long roomId;
    private String roomName;
    private int userCount;
    private int maxUserCnt;

    private Map<String, WebSocketSession> WaitingRoomclients;
    private Map<String, WebSocketSession> MeetingRoomclients;
}