package com.example.tingle.webRTC;

import lombok.*;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;

@Data
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MeetingRoom {

    private Long roomId;
    private String roomName;
    private String roomPwd;
    private int userCount;
    private int maxUserCnt;

    private Map<String, WebSocketSession> clients;
}