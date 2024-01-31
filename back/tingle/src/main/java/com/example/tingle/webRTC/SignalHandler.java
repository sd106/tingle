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
        System.out.println("들어왔네 : " + session);
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
        String roomType = signalData.getRoomType();
        StarEntity star;
        long roomId;

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
                // data 에 starname이 들어온다
                star = starRepository.findByUsername(data);
                System.out.println("star: " + star.getId() + "한테 입장중~~");
                System.out.println("roomType: " + roomType);
                // roomType에 따라 roomId를 다르게 찾는다 (대기방 => *10 +1, 미팅방 => *10 + 2)
                if (roomType.equals("Waiting")) {
                    roomId = star.getId()*10 + 1;
                    System.out.println("대기방이군");
                } else if (roomType.equals("Meeting")) {
                    roomId = star.getId()*10 + 2;
                    System.out.println("미팅방이군");
                } else {
                    return;
                }

                // room id로 방을 찾는다
                FanMeetingRoom room2 = fanMeetingRoomService.findRoomById(roomId);

                System.out.println("방번호는??: " + room2);

                SignalData sd = SignalData.builder()
                        .sender("Server")
                        .signalType("Join")
                        .data(data)
                        .iceCandidate(null)
                        .sdp(null)
                        .build();
                System.out.println(sender);
                System.out.println(session);
                fanMeetingRoomService.addClient(room2, sender, session);
                sessionIdToRoomMap.put(session.getId(), room2);
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(sd)));

                break;
            case "Leave":
                FanMeetingRoom room3 = sessionIdToRoomMap.get(session.getId());

                Optional<String> client = fanMeetingRoomService.getClients(room3).entrySet().stream()
                        .filter(entry -> Objects.equals(entry.getValue().getId(), session.getId()))
                        .map(Map.Entry::getKey)
                        .findAny();
                client.ifPresent(c -> fanMeetingRoomService.removeClientByName(room3, c));
                sessionIdToRoomMap.remove(session.getId());
                break;
            case "Text":
                System.out.println("Text: " + data);
                FanMeetingRoom room4 = sessionIdToRoomMap.get(session.getId());

                Map<String, WebSocketSession> clients = fanMeetingRoomService.getClients(room4);
                System.out.println("??");
                System.out.println(room4);
                System.out.println(clients);
                for (Map.Entry<String, WebSocketSession> c : clients.entrySet()) {
                    SignalData sd2 = SignalData.builder()
                            .data(data)
                            .iceCandidate(null)
                            .sdp(null)
                            .sender(sender)
                            .signalType(signalData.getSignalType())
                            .build();
                    c.getValue().sendMessage(new TextMessage(objectMapper.writeValueAsString(sd2)));
                }
                break;

                // 다음 팬에게 초대장 보내는 신호
            case "Invite":
                star = starRepository.findByUsername(data);

                // roomType에 따라 roomId를 다르게 찾는다 (대기방 => *10 +1, 미팅방 => *10 + 2)
                long waitingRoomId = star.getId() * 10 + 1;


                // waitingRoom에 있는 팬을 찾아 메시지를 보낸다.
                FanMeetingRoom waitingRoom = fanMeetingRoomService.findRoomById(waitingRoomId);
                Map<String, WebSocketSession> waitingClients = fanMeetingRoomService.getClients(waitingRoom);

                // data 안에는 초대할 팬의 이름이 들어온다.
                WebSocketSession waitingclient = waitingClients.get(data);

                if (waitingclient != null) {
                    SignalData inviteMessage = SignalData.builder()
                            .sender("Server")
                            .signalType("Invite")
                            .data(null)
                            .iceCandidate(null)
                            .sdp(null)
                            .build();
                    waitingclient.sendMessage(new TextMessage(objectMapper.writeValueAsString(inviteMessage)));
                } else {
                    System.out.println("Client to invite not found in the waiting room: " + data);
                }

                break;
//
//                // 팬미팅 중인 팬과 연결을 종료
//            case "FinishFan":
//
//                // data 에 star name이 들어온다
//                star = starRepository.findByUsername(data);
//
//                // roomType에 따라 roomId를 다르게 찾는다 (대기방 => *10 +1, 미팅방 => *10 + 2)
//                waitingRoomId = star.getId() * 10 + 1;
//                meetingRoomId = star.getId() * 10 + 2;
//
//                fanMeetingRoom = sessionIdToRoomMap.get(session.getId());
//                break;
        }
    }
}

