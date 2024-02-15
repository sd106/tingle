package com.example.tingle.webRTC;

import com.example.tingle.fanMeeting.dto.FanMeetingReservationMessageDto;
import com.example.tingle.fanMeeting.entity.FanMeetingReservation;
import com.example.tingle.fanMeeting.model.FanMeetingRoom;
import com.example.tingle.fanMeeting.repository.FanMeetingReservationRepository;
import com.example.tingle.fanMeeting.service.FanMeetingRoomService;
import com.example.tingle.fanMeeting.utils.MeetingRoomMap;
import com.example.tingle.star.entity.StarEntity;
import com.example.tingle.star.repository.StarRepository;
import com.example.tingle.user.entity.UserEntity;
import com.example.tingle.user.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final StarRepository starRepository;
    private final FanMeetingReservationRepository fanMeetingReservationRepository;
    private final Map<Long, FanMeetingRoom> chatRooms = MeetingRoomMap.getInstance().getMeetingRooms();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Map<String, FanMeetingRoom> sessionIdToRoomMap = new HashMap<>();
    private static SenderState server = new SenderState("Server");

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

        System.out.println("handleTextMessage: " + signalData.getSignalType());
        SenderState sender = signalData.getSender();
        String data = signalData.getData();
        String roomType = signalData.getRoomType();
        StarEntity star;
        UserEntity fan;

        long roomId;
        long waitingRoomId;
        long meetingRoomId;

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
                        if (!client.getKey().equals(sender.getUsername())) {
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
                // data 에 starid이 들어온다
                star = starRepository.findStarEntityById(Long.parseLong(data)).orElseThrow(() -> new IllegalArgumentException("해당하는 star가 없습니다."));

                System.out.println("star: " + star.getId() + "한테 입장중~~");
                System.out.println("roomType: " + roomType);
                // roomType에 따라 roomId를 다르게 찾는다 (대기방 => *10 +1, 미팅방 => *10 + 2)
                if (roomType.equals("Waiting")) {
                    roomId = star.getId() * 10 + 1;
                    System.out.println("대기방이군");
                } else if (roomType.equals("Meeting")) {
                    roomId = star.getId() * 10 + 2;
                    System.out.println("미팅방이군");
                } else {
                    return;
                }

                // room id로 방을 찾는다
                FanMeetingRoom room2 = fanMeetingRoomService.findRoomById(roomId);

                System.out.println("방번호는??: " + room2);

                SignalData sd = SignalData.builder()
                        .sender(server)
                        .signalType("Join")
                        .data(data)
                        .iceCandidate(null)
                        .sdp(null)
                        .build();
                System.out.println(sender.getUsername());
                System.out.println(session);
                fanMeetingRoomService.addClient(room2, sender.getUsername(), session);
                sessionIdToRoomMap.put(session.getId(), room2);
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(sd)));

                // 대기자 목록을 받기 위해 메시지 처리
                if (roomType.equals("Waiting")) {
                    SignalData sd2 = SignalData.builder()
                            .sender(sender) // 또는 적절한 발신자 설정
                            .signalType("Join2")
                            .data("새로운 사용자가 대기방에 입장했습니다.")
                            .iceCandidate(null)
                            .sdp(null)
                            .build();

                    // 대기방에 있는 모든 클라이언트(스타 포함)에게 메시지를 보낸다
                    Map<String, WebSocketSession> waitingClients = fanMeetingRoomService.getClients(room2);
                    for (WebSocketSession clientSession : waitingClients.values()) {
                        clientSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(sd2)));
                    }
                }

                // 스타한테 메시지 보내기 sd2

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
                star = starRepository.findByUsername(sender.getUsername());

                // roomType에 따라 roomId를 다르게 찾는다 (대기방 => *10 +1, 미팅방 => *10 + 2)
                waitingRoomId = star.getId() * 10 + 1;

                // waitingRoom에 있는 팬을 찾아 메시지를 보낸다.
                FanMeetingRoom waitingRoom = fanMeetingRoomService.findRoomById(waitingRoomId);
                Map<String, WebSocketSession> waitingClients = fanMeetingRoomService.getClients(waitingRoom);

                // data 안에는 초대할 팬의 이름이 들어온다.
                WebSocketSession waitingClient = waitingClients.get(data);

                if (waitingClient != null) {
                    SignalData inviteMessage = SignalData.builder()
                            .sender(server)
                            .signalType(signalData.getSignalType())
                            .data(null)
                            .iceCandidate(null)
                            .sdp(null)
                            .build();
                    waitingClient.sendMessage(new TextMessage(objectMapper.writeValueAsString(inviteMessage)));
                } else {
                    System.out.println("Client to invite not found in the waiting room: " + data);
                }

                break;

            case "Accept":
                fan = userRepository.findByUsername(sender.getUsername());
                FanMeetingReservation fanMeetingReservation = fanMeetingReservationRepository.findByUser(fan);
                FanMeetingReservationMessageDto fanMeetingReservationMessageDto = FanMeetingReservationMessageDto.builder()
                        .orderAt(fanMeetingReservation.getOrderAt())
                        .userName(fanMeetingReservation.getUser().getUsername())
                        .starName(fanMeetingReservation.getStar().getUsername())
                        .fanMeetingType(fanMeetingReservation.getFanMeetingType().getName())
                        .build();
                star = starRepository.findStarEntityById(Long.parseLong(data)).orElseThrow(() -> new IllegalArgumentException("해당하는 star가 없습니다."));

                SignalData acceptMessaege = SignalData.builder()
                        .sender(server)
                        .signalType(signalData.getSignalType())
                        .data(null)
                        .iceCandidate(null)
                        .sdp(fanMeetingReservationMessageDto)
                        .build();

                meetingRoomId = star.getId() * 10 + 2;

                FanMeetingRoom meetingRoom = fanMeetingRoomService.findRoomById(meetingRoomId);
                Map<String, WebSocketSession> meetingClients = fanMeetingRoomService.getClients(meetingRoom);

                // data 안에는 초대할 팬의 이름이 들어온다.
                WebSocketSession meetingClient = meetingClients.get(star.getUsername());

                meetingClient.sendMessage(new TextMessage(objectMapper.writeValueAsString(acceptMessaege)));

//                // 팬미팅 중인 팬과 연결을 종료
            case "FinishMeeting":
                FanMeetingRoom room5 = sessionIdToRoomMap.get(session.getId());

                Map<String, WebSocketSession> meetingClients2 = fanMeetingRoomService.getClients(room5);
                System.out.println(data);
                WebSocketSession finishedClient = meetingClients2.get(data);

                fanMeetingRoomService.removeClientByName(room5, data);

                sessionIdToRoomMap.remove(finishedClient.getId());
                break;
        }
    }
}

