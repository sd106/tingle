package com.example.tingle.chat.controller;

import com.example.tingle.chat.dto.ChatMessageDto;
import com.example.tingle.chat.dto.ChatRoomDto;
import com.example.tingle.chat.dto.request.ChatMessageRequest;
import com.example.tingle.chat.repository.ChatMessageRepository;
import com.example.tingle.chat.service.ChatMessageService;
import com.example.tingle.wish.dto.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chatMessage")
public class ChatMessageController {

    @Autowired
    private ChatMessageService chatMessageService;

    // 해당 스타의 모든 메시지 조회
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/readMessages/{starId}")
    public Response findMessagesByStarId(@PathVariable Long starId) {
        try {
            List<ChatMessageDto> list = chatMessageService.findMessagesByStarId(starId);

            if(list.isEmpty())
                return new Response("success", "findMessagesByStarId", list);
            else
                return new Response("success", "findMessagesByStarId", list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response("fail", "findMessagesByStarId", null);
        }
    }

    // 메시지 저장
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/saveMessages")
    public Response saveMessages(@RequestBody ChatMessageRequest chatMessageRequest) {
        try {
            chatMessageService.saveMessages(chatMessageRequest);

            return new Response("success", "saveMessages", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response("fail", "saveMessages", null);
        }
    }

    // 해당 스타의 모든 메시지 삭제
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/deleteMessages/{starId}")
    public Response/*List<ChatRomDto>*/ deleteMessages(@PathVariable Long starId) {
        try {
            chatMessageService.deleteMessages(starId);

            return new Response("success", "deleteMessages", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response("fail", "findAddingRoomsByUserId", null);
        }
    }
}