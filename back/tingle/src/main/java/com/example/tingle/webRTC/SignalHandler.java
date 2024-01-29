package com.example.tingle.webRTC;

import com.example.tingle.fanMeeting.model.FanMeetingRoom;
import com.example.tingle.fanMeeting.service.FanMeetingRoomService;
import com.example.tingle.fanMeeting.utils.MeetingRoomMap;
import com.example.tingle.user.entity.StarEntity;
import com.example.tingle.user.repository.StarRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SignalHandler extends TextWebSocketHandler {

    private final FanMeetingRoomService fanMeetingRoomService;
    private final StarRepository starRepository;
    private final Map<Long, FanMeetingRoom> chatRooms = MeetingRoomMap.getInstance().getMeetingRooms();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Map<String, FanMeetingRoom> sessionIdToRoomMap = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 대기방, 미팅방 각각의 현재 구성원 여부를 data에 담는다.
        if (data.equals("waiting")){
            data = Boolean.toString(!fanMeetingRoom.getWaitingRoomclients().isEmpty());
        } else if (data.equals("meeting")) {
            data = Boolean.toString(!fanMeetingRoom.getMeetingRoomclients().isEmpty());
        }

        SignalData sd = SignalData.builder()
                .sender("Server")
                .signalType("Join")
                .data(data)
                .iceCandidate(null)
                .sdp(null)
                .build();
        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(sd)));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("afterConnectionClosed: " + session);
        FanMeetingRoom fanMeetingRoom = sessionIdToRoomMap.get(session.getId());
        Optional<String> client = fanMeetingRoomService.getClients(fanMeetingRoom).entrySet().stream()
                .filter(entry -> Objects.equals(entry.getValue().getId(), session.getId()))
                .map(Map.Entry::getKey)
                .findAny();
        client.ifPresent(c -> fanMeetingRoomService.removeClientByName(fanMeetingRoom, c));
        sessionIdToRoomMap.remove(session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        SignalData signalData = objectMapper.readValue(message.getPayload(), SignalData.class);

        String sender = signalData.getSender();
        String data = signalData.getData();
        FanMeetingRoom fanMeetingRoom;

        switch (signalData.getSignalType()) {
            case "Offer":
            case "Answer":
            case "Ice":
                Object iceCandidate = signalData.getIceCandidate();
                Object sdp = signalData.getSdp();
                FanMeetingRoom room = sessionIdToRoomMap.get(session.getId());

                if (room != null) {
                    Map<String, WebSocketSession> clients = fanMeetingRoomService.getClients(room);

                    for (Map.Entry<String, WebSocketSession> client : clients.entrySet()) {
                        if (!client.getKey().equals(sender)) {
                            SignalData sd = SignalData.builder()
                                    .data(data)
                                    .iceCandidate(iceCandidate)
                                    .sdp(sdp)
                                    .sender(sender)
                                    .signalType(signalData.getSignalType())
                                    .build();
                            client.getValue().sendMessage(new TextMessage(objectMapper.writeValueAsString(sd)));
                        }
                    }

                }
                break;
            case "Join":
                // star의 id와 room id는 같게 생성되었으므로 찾을 때도 똑같이 찾을 수 있다.
                StarEntity star = starRepository.findByUsername(data);

                fanMeetingRoom = fanMeetingRoomService.findRoomById(star.getId());

                fanMeetingRoomService.addClient(fanMeetingRoom, sender, session);
                sessionIdToRoomMap.put(session.getId(), fanMeetingRoom);

                break;
            case "Leave":
                fanMeetingRoom = sessionIdToRoomMap.get(session.getId());

                Optional<String> client = fanMeetingRoomService.getClients(fanMeetingRoom).entrySet().stream()
                        .filter(entry -> Objects.equals(entry.getValue().getId(), session.getId()))
                        .map(Map.Entry::getKey)
                        .findAny();
                client.ifPresent(c -> fanMeetingRoomService.removeClientByName(fanMeetingRoom, c));
                sessionIdToRoomMap.remove(session.getId());
                break;
            case "Text":
                fanMeetingRoom = sessionIdToRoomMap.get(session.getId());

                Map<String, WebSocketSession> clients = fanMeetingRoomService.getClients(fanMeetingRoom);

                for (Map.Entry<String, WebSocketSession> c : clients.entrySet()) {
                    if (!c.getKey().equals(sender)) {
                        SignalData sd2 = SignalData.builder()
                                .data(data)
                                .iceCandidate(null)
                                .sdp(null)
                                .sender(sender)
                                .signalType(signalData.getSignalType())
                                .build();
                        c.getValue().sendMessage(new TextMessage(objectMapper.writeValueAsString(sd2)));
                    }
                }
                break;
            case "NextFan":
                fanMeetingRoom = sessionIdToRoomMap.get(session.getId());

                fanMeetingRoom.getWaitingRoomclients().remove(sender);
                fanMeetingRoom.getWaitingRoomclients().put(sender, session);
                break;
            case "FinishFan":
                fanMeetingRoom = sessionIdToRoomMap.get(session.getId());

                fanMeetingRoom.getMeetingRoomclients().remove(sender);
                break;
        }
    }
}

