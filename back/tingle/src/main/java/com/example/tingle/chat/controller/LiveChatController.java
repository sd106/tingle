package com.example.tingle.chat.controller;

import com.example.tingle.chat.dto.ChatMessageDto;
import com.example.tingle.chat.dto.request.ChatMessageRequest;
import com.example.tingle.chat.service.ChatMessageService;
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

import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
public class LiveChatController {

    @Autowired
    private ChatMessageService chatMessageService;

    // 특정 Broker로 메세지를 전달
    @Autowired
    private SimpMessagingTemplate template;

    /* /sub/room/{roomId} */
    /* /pub/message/ */

    @MessageMapping("/message/{roomId}")
    // @SendTo("/sub/room/{roomId}")
    public void messagesChat(@DestinationVariable Long roomId, ChatMessageRequest chatMessageRequest) {

        template.convertAndSend("/sub/room/" + roomId, chatMessageRequest.getMessage());

        log.info("Message [{}] send by member: chatting room: {}", chatMessageRequest.getMessage(), roomId);

        // 전송 후에 저장한다.
        chatMessageService.saveMessages(chatMessageRequest);
    }
}
