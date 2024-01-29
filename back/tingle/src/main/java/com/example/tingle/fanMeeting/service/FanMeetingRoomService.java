package com.example.tingle.fanMeeting.service;

import com.example.tingle.fanMeeting.model.FanMeetingRoom;
import com.example.tingle.fanMeeting.utils.MeetingRoomMap;
import com.example.tingle.user.entity.StarEntity;
import com.example.tingle.user.repository.StarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FanMeetingRoomService {

    private final Map<Long, FanMeetingRoom> meetingRooms = MeetingRoomMap.getInstance().getMeetingRooms();
    private final StarRepository starRepository;

    // 팬미팅 이름, star의 id를 roomId로 채팅방 생
    public FanMeetingRoom createChatRoom(String roomName, Integer maxUserCnt, String starName) {
        StarEntity star = starRepository.findByUsername(starName);
        Long roomId = star.getId();

        return FanMeetingRoom.builder()
                .roomId(roomId)
                .roomName(roomName)
                .userCount(0)
                .maxUserCnt(maxUserCnt)
                .build();

    }

    public Map<String, WebSocketSession> getClients(final FanMeetingRoom room) {
        return Optional.ofNullable(room)
                .map(r -> Collections.unmodifiableMap(r.getClients()))
                .orElse(Collections.emptyMap());
    }

    public void addClient(FanMeetingRoom room, String name, WebSocketSession session) {
        room.getClients().put(name, session);
    }

    public void removeClientByName(FanMeetingRoom room, String name) {
        room.getClients().remove(name);
    }


    public void addRoom(FanMeetingRoom room) {
        meetingRooms.put(room.getRoomId(), room);
    }

    public Long getRoomId(FanMeetingRoom room) {
        return room.getRoomId();
    }


    public FanMeetingRoom findRoomById(Long roomId) {
        return MeetingRoomMap.getInstance().getMeetingRooms().get(roomId);
    }

}
