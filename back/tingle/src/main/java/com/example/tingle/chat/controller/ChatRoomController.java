package com.example.tingle.chat.controller;

import com.example.tingle.chat.dto.ChatRoomDto;
import com.example.tingle.chat.service.ChatRoomService;
import com.example.tingle.wish.dto.WishDto;
import com.example.tingle.wish.dto.response.Response;
import com.example.tingle.wish.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatRoomController {

    @Autowired
    private ChatRoomService chatRoomService;

    // 구독 & 추가한 스타들의 채팅 방 조회
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/read/addingRooms/{userId}")
    public Response/*List<ChatRomDto>*/ findAddingRoomsByUserId(@PathVariable Long userId) {
        try {
            List<ChatRoomDto> list = chatRoomService.findRoomsByUserId(userId);

            if(list.isEmpty())
                return new Response("success", "findAddingRoomsByUserId", list);
            else
                return new Response("success", "findAddingRoomsByUserId", list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response("fail", "findAddingRoomsByUserId", null);
        }
    }

    // 구독한 스타들의 채팅 방 조회
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/read/followingRooms/{userId}")
    public Response/*List<ChatRoomDto>*/ findfollowingRoomsByUserId(Long userId) {
        try {
            List<ChatRoomDto> list = chatRoomService.findRoomsByUserId(userId);

            if(list.isEmpty())
                return new Response("success", "findAddingRoomsByUserId", list);
            else
                return new Response("success", "findAddingRoomsByUserId", list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response("fail", "findAddingRoomsByUserId", null);
        }
    }
//
//    // 해당 채팅 방 조회 (스타)
//    public ChatRoomDto findTheRoomByStarId(Long starId) {
//
//    }
//
//    // 해당 스타의 채팅 방 생성 (스타 가입 시)
//    public void saveTheRoomByStarId(Long starId) {
//
//    }
//
//    // 해당 스타의 채팅 방 삭제 (스타 탈퇴 시)
//    public void deleteByStarId(Long starId) {
//
//    }
//
//    // 해당 스타의 채팅방으로 입장 (팬)
//    public void EnterByUserId(Long starId, Long userId) {
//
//    }
//
//    // 해당 스타의 채팅방에서 퇴장 (팬)
//    public void ExitByUserId(Long starId, Long userId) {
//
//    }
}
