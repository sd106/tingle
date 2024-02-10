package com.example.tingle.chat.controller;

import com.example.tingle.chat.dto.ChatMessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class LiveChatController {

    // 특정 Broker로 메세지를 전달
    @Autowired
    private SimpMessagingTemplate template;

    // Client가 SEND 할 수 있는 경로
    // stompConfig 에서 설정한 applicationDestinationPrefixes 와 @MessageMapping 경로가 병합됨
    // "/pub/chat/enter"
    @MessageMapping(value = "/chat/enter")
    public void enter(ChatMessageDto chatMessageDto){
        chatMessageDto.setMessage(chatMessageDto.getId() + "님 안녕하세요!");
        template.convertAndSend("/sub/chat/room/" + chatMessageDto.getRoomId(), chatMessageDto.getMessage());
    }

    @MessageMapping(value = "/chat/message")
    public void message(ChatMessageDto chatMessageDto){
        template.convertAndSend("/sub/chat/room/" + chatMessageDto.getRoomId(), chatMessageDto);
    }
}
