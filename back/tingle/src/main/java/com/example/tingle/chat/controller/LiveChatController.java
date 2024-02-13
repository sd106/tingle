package com.example.tingle.chat.controller;

import com.example.tingle.chat.dto.ChatMessageDto;
import com.example.tingle.chat.dto.request.ChatMessageRequest;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
@Slf4j
public class LiveChatController {

    // 특정 Broker로 메세지를 전달
    @Autowired
    private SimpMessagingTemplate template;

    // Client가 SEND 할 수 있는 경로
    // stompConfig 에서 설정한 applicationDestinationPrefixes 와 @MessageMapping 경로가 병합됨
    // "/pub/chat/enter"
    @MessageMapping("/chat/{roomId}/message")
    public void messagesChat(@DestinationVariable Long roomId, ChatMessageRequest chatMessageRequest) {
        template.convertAndSend("/sub/chat/" + roomId, chatMessageRequest.getMessage());
        log.info("Message [{}] send by member: chatting room: {}", chatMessageRequest.getMessage(), roomId);
    }
}
