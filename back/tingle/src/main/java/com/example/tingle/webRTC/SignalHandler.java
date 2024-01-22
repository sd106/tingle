package com.example.tingle.webRTC;

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

    private final MeetingService meetingService;
    private final Map<Long, MeetingRoom> chatRooms = MeetingRoomMap.getInstance().getMeetingRooms();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Map<String, MeetingRoom> sessionIdToRoomMap = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        if (chatRooms.isEmpty()) {
            MeetingRoom room = MeetingRoom.builder()
                    .roomId(1L)
                    .roomName("1번방")
                    .roomPwd("password") // 채팅방 패스워드
                    .userCount(0) // 채팅방 참여 인원수
                    .maxUserCnt(2) // 최대 인원수 제한
                    .clients(new HashMap<String, WebSocketSession>())
                    .build();

            chatRooms.put(room.getRoomId(), room);

        }

        System.out.println("들어왔따~~");

        SignalData sd = SignalData.builder()
                .sender("Server")
                .signalType("Join")
                .data(Boolean.toString(!sessionIdToRoomMap.isEmpty()))
                .iceCandidate(null)
                .sdp(null)
                .build();
        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(sd)));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        MeetingRoom meetingRoom = sessionIdToRoomMap.get(session.getId());

        Optional<String> client = meetingService.getClients(meetingRoom).entrySet().stream()
                .filter(entry -> Objects.equals(entry.getValue().getId(), session.getId()))
                .map(Map.Entry::getKey)
                .findAny();
        client.ifPresent(c -> meetingService.removeClientByName(meetingRoom, c));
        sessionIdToRoomMap.remove(session.getId());

        System.out.println("나갔다~~");
        System.out.println("현재 존재하는 방 ID들:");
        for (Long roomId : chatRooms.keySet()) {
            System.out.println("방 ID: " + roomId);
            Map<String, WebSocketSession> clients = meetingService.getClients(chatRooms.get(roomId));
            for (String d : clients.keySet()) {
                System.out.println("멤버: " + d);
            }
        }
        super.afterConnectionClosed(session, status);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        SignalData signalData = objectMapper.readValue(message.getPayload(), SignalData.class);

        String sender = signalData.getSender();
        String data = signalData.getData();
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println(sender + " 로 부터 " + signalData.getSignalType());
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@");

        MeetingRoom meetingRoom;

        switch (signalData.getSignalType()) {
            case "Offer":
            case "Answer":
            case "Ice":
                Object iceCandidate = signalData.getIceCandidate();
                Object sdp = signalData.getSdp();
                System.out.println("[ws] offer offer");
                MeetingRoom room = sessionIdToRoomMap.get(session.getId());

                if (room != null) {
                    Map<String, WebSocketSession> clients = meetingService.getClients(room);

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
            case "Chat":
                break;
            case "Join":
                System.out.println("[ws] " + sender + " has joined Room: #" + data);

                meetingRoom = meetingService.findRoomById(Long.parseLong(data));

                meetingService.addClient(meetingRoom, sender, session);
                sessionIdToRoomMap.put(session.getId(), meetingRoom);

                System.out.println("현재 존재하는 방 ID들:");
                for (Long roomId : chatRooms.keySet()) {
                    System.out.println("방 ID: " + roomId);
                    Map<String, WebSocketSession> clients = meetingService.getClients(chatRooms.get(roomId));
                    for (String d : clients.keySet()) {
                        System.out.println("멤버: " + d);
                    }
                }
                break;
            case "Leave":
                System.out.println("[ws] {} is going to leave Room: #{}" + sender + data);

                meetingRoom = sessionIdToRoomMap.get(session.getId());

                Optional<String> client = meetingService.getClients(meetingRoom).entrySet().stream()
                        .filter(entry -> Objects.equals(entry.getValue().getId(), session.getId()))
                        .map(Map.Entry::getKey)
                        .findAny();
                client.ifPresent(c -> meetingService.removeClientByName(meetingRoom, c));
                sessionIdToRoomMap.remove(session.getId());
                break;
        }
    }
}

