package com.example.tingle.webRTC;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MeetingService {

    private final Map<Long, MeetingRoom> meetingRooms = MeetingRoomMap.getInstance().getMeetingRooms();

    public MeetingRoom createChatRoom(String roomName, String roomPwd, Integer maxUserCnt, Long roomId) {

        return MeetingRoom.builder()
                .roomId(roomId)
                .roomName(roomName)
                .roomPwd(roomPwd)
                .userCount(0)
                .maxUserCnt(maxUserCnt)
                .build();

    }

    public Map<String, WebSocketSession> getClients(final MeetingRoom room) {
        return Optional.ofNullable(room)
                .map(r -> Collections.unmodifiableMap(r.getClients()))
                .orElse(Collections.emptyMap());
    }

    public void addClient(MeetingRoom room, String name, WebSocketSession session) {
        room.getClients().put(name, session);
    }

    public void removeClientByName(MeetingRoom room, String name) {
        room.getClients().remove(name);
    }


    public void addRoom(MeetingRoom room) {
        meetingRooms.put(room.getRoomId(), room);
    }

    public Long getRoomId(MeetingRoom room) {
        return room.getRoomId();
    }


    public MeetingRoom findRoomById(Long roomId) {
        return MeetingRoomMap.getInstance().getMeetingRooms().get(roomId);
    }
}
