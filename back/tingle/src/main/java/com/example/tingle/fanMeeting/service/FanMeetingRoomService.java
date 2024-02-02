package com.example.tingle.fanMeeting.service;

import com.example.tingle.fanMeeting.dto.request.CreateFanMeetingRoomRequest;
import com.example.tingle.fanMeeting.model.FanMeetingRoom;
import com.example.tingle.fanMeeting.utils.MeetingRoomMap;
import com.example.tingle.star.entity.StarEntity;
import com.example.tingle.star.repository.StarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FanMeetingRoomService {

    private final Map<Long, FanMeetingRoom> meetingRooms = MeetingRoomMap.getInstance().getMeetingRooms();
    private final StarRepository starRepository;

    // 팬미팅 이름, star의 id를 roomId로 채팅방 생성
    public FanMeetingRoom createRoom(CreateFanMeetingRoomRequest request) {
        System.out.println("??");
        StarEntity star = starRepository.findByUsername(request.getStarName());
        System.out.println(star);
        // 대기방, 미팅방 id는 star의 id * 10 + (1 or 2)
        Long waitingRoomId = star.getId() * 10 + 1;
        Long meetingRoomId = star.getId() * 10 + 2;
        Map<String, WebSocketSession> clients1 = new HashMap<>();
        Map<String, WebSocketSession> clients2 = new HashMap<>();

        FanMeetingRoom fanwaitingRoom = FanMeetingRoom.builder()
                .roomId(waitingRoomId)
                .roomName(request.getRoomName())
                .roomType("waiting")
                .userCount(0)
                .clients(clients1)
                .maxUserCnt(request.getMaxUserCnt())
                .build();

        FanMeetingRoom fanMeetingRoom = FanMeetingRoom.builder()
                .roomId(meetingRoomId)
                .roomName(request.getRoomName())
                .roomType("meeting")
                .userCount(0)
                .clients(clients2)
                .maxUserCnt(2)
                .build();

        System.out.println("대기방 :" + fanwaitingRoom);
        System.out.println("미팅방 :" + fanMeetingRoom);
        addRoom(fanwaitingRoom);
        addRoom(fanMeetingRoom);
        System.out.println("완성!!");
        return fanMeetingRoom;
    }

    public Map<String, WebSocketSession> getClients(final FanMeetingRoom room) {
        return Optional.ofNullable(room)
                .map(r -> Collections.unmodifiableMap(r.getClients()))
                .orElse(Collections.emptyMap());
    }

    public void addClient(FanMeetingRoom room, String name, WebSocketSession session) {
        System.out.println(room);
        System.out.println(name);
        System.out.println(session);
        room.getClients().put(name, session);
    }

    public void removeClientByName(FanMeetingRoom room, String name) {
        room.getClients().remove(name);
    }

    public void addRoom(FanMeetingRoom room) {
        meetingRooms.put(room.getRoomId(), room);
    }

    public FanMeetingRoom findRoomById(Long roomId) {
        return MeetingRoomMap.getInstance().getMeetingRooms().get(roomId);
    }

}
