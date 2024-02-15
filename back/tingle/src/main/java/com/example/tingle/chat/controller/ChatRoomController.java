package com.example.tingle.chat.controller;

import com.example.tingle.chat.dto.ChatRoomDto;
import com.example.tingle.chat.service.ChatRoomService;
import com.example.tingle.wish.dto.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chatRoom")
public class ChatRoomController {

    @Autowired
    private ChatRoomService chatRoomService;

    // 구독 & 추가한 스타들의 채팅방 조회
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/readRooms/{userId}")
    public Response findAddingRoomsByUserId(@PathVariable Long userId) {
        try {
            List<ChatRoomDto> list = chatRoomService.findRoomsByUserId(userId);

            if(list.isEmpty())
                return new Response("success", "findAddingRoomsByUserId", null);
            else
                return new Response("success", "findAddingRoomsByUserId", list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response("fail", "findAddingRoomsByUserId", null);
        }
    }

    // 해당 채팅방 조회 (스타)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/readTheRoom/{starId}")
    public Response findTheRoomByStarId(@PathVariable Long starId) {
        try {
            ChatRoomDto chatRoomDto = chatRoomService.findTheRoomByStarId(starId);

            return new Response("success", "findTheRoomByStarId", chatRoomDto);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response("fail", "findTheRoomByStarId", null);
        }
    }

    // 해당 스타의 채팅방 생성 (스타 가입 시)
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/saveTheRoom/{starEmail}")
    public Response saveTheRoomByStarEmail(@PathVariable String starEmail) {
        try {
            chatRoomService.saveTheRoomByStarEmail(starEmail);

            return new Response("success", "saveTheRoomByStarEmail", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response("fail", "saveTheRoomByStarEmail", null);
        }
    }

    // 해당 스타의 채팅방 삭제 (스타 탈퇴 시)
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/deleteTheRoom/{starId}")
    public Response deleteByStarId(@PathVariable Long starId) {
        try {
            chatRoomService.deleteByStarId(starId);

            return new Response("success", "deleteByStarId", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response("fail", "deleteByStarId", null);
        }
    }

    // 해당 스타의 채팅방으로 입장 (팬)
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/enterTheRoom/{userId}/{starId}")
    public Response EnterByUserId(@PathVariable Long starId, @PathVariable Long userId) {
        try {
            chatRoomService.EnterByUserId(starId, userId);

            return new Response("success", "EnterByUserId", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response("fail", "EnterByUserId", null);
        }
    }

    // 해당 스타의 채팅방에서 퇴장 (팬)
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/exitTheRoom/{userId}/{starId}")
    public Response ExitByUserId(@PathVariable Long starId, @PathVariable Long userId) {
        try {
            chatRoomService.ExitByUserId(starId,userId);

            return new Response("success", "ExitByUserId", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response("fail", "ExitByUserId", null);
        }
    }
}
