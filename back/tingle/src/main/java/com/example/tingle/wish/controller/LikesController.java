package com.example.tingle.wish.controller;

import com.example.tingle.wish.dto.LikesDto;
import com.example.tingle.wish.dto.response.Response;
import com.example.tingle.wish.service.LikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/likes")
public class LikesController {

    @Autowired
    private LikesService likesService;

    // 해당 스타 위시에 대한 내 추천 목록 조회
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/read/mylist/{starId}/{userId}")
    public Response readMyLikedList(@PathVariable Long starId, @PathVariable Long userId) {

        try {
            List<LikesDto> list = likesService.readLikesList(starId, userId);

            if(list.isEmpty())
                return new Response("success", "readMyLikedList", list);
            else
                return new Response("success", "readMyLikedList", list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response("fail", "readMyLikedList", null);
        }
    }

    // 해당 위시에 대한 내 추천 상태 조회
//    @ResponseStatus(HttpStatus.OK)
//    @GetMapping("/read/my/{wishId}/{userId}")
//    public Response readMyLiked(@PathVariable Long wishId, @PathVariable Long userId) {
//
//        try {
//            int result = likesService.isLikes(wishId, userId);
//
//            if(result == 0)
//                return new Response("success", "readMyLiked", 0);
//            else if(result == 1)
//                return new Response("success", "readMyLiked", 1);
//            else
//                return new Response("success", "readMyLiked", 2);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new Response("fail", "readMyLiked", null);
//        }
//    }

    // 해당 위시에 대한 추천 상태 토글 변경 (create 포함)
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/update/toggle/{wishId}/{userId}")
    public Response updateWishLiked(@PathVariable Long wishId, @PathVariable Long userId) {

        try {
            int result = likesService.updateLikes(wishId, userId);

            if(result == 0)
                return new Response("success", "updateWishLiked", 0);
            else if(result == 1)
                return new Response("success", "updateWishLiked", 1);
            else
                return new Response("success", "updateWishLiked", 2);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response("fail", "updateWishLiked", null);
        }
    }
}
