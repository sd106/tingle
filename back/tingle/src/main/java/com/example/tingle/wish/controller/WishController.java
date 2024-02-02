package com.example.tingle.wish.controller;

import com.example.tingle.wish.dto.WishDto;
import com.example.tingle.wish.dto.request.WishRequest;
import com.example.tingle.wish.dto.response.Response;
import com.example.tingle.wish.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wish")
public class WishController {

    @Autowired
    private WishService wishService;

    // 해당 스타의 위시 조회 (추천 수 높은 순)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/read/withLikes/{starId}/{opt}")
    public Response readWishWithLikes(@PathVariable Long starId, @PathVariable int opt) {

        try {
            List<WishDto> list = wishService.readWishes(starId, opt);

            if(list.isEmpty())
                return new Response("success", "readWishWithLikes", "null");
            else
                return new Response("success", "readWishWithLikes", list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response("fail", "readWishWithLikes", -1);
        }
    }

    // 위시 등록

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/save")
    public Response saveWish(@RequestBody WishRequest wishRequest) {

        try {
            wishService.saveWish(wishRequest);
            return new Response("success", "saveWish", "null");
        } catch(Exception e) {
            e.printStackTrace();
            return new Response("fail", "saveWish", -1);
        }
    }

    // 해당 위시 수정 (미구현 상태)
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/update")
    public Response updateWish(@RequestBody WishRequest wishRequest) {

        try {
            wishService.updateWish(wishRequest);
            return new Response("success", "updateWish", "null");
        } catch(Exception e) {
            e.printStackTrace();
            return new Response("fail", "updateWish", -1);
        }
    }

    // 해당 위시 삭제
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/delete/{wishId}")
    public Response deleteWish(@PathVariable Long wishId) {

        try {
            wishService.deleteWish(wishId);
            return new Response("success", "deleteWish", "null");
        } catch(Exception e) {
            e.printStackTrace();
            return new Response("fail", "deleteWish", -1);
        }
    }

    // 해당 위시에 미션금 추가
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/points/add/{wishId}/{userId}/{points}")
    public Response addPoints(@PathVariable Long wishId, @PathVariable Long userId, @PathVariable int points) {

        try {
            wishService.addPoints(wishId, userId, points);
            return new Response("success", "addPoints", "null");
        } catch(Exception e) {
            e.printStackTrace();
            return new Response("fail", "addPoints", -1);
        }
    }

    // 해당 위시를 미채택/채택/완료 상태로 변경
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/status/update/{wishId}/{wishStatus}")
    public Response updateWishStatus(@PathVariable Long wishId, @PathVariable int wishStatus) {

        try {
            wishService.updateWishStatus(wishId, wishStatus);
            return new Response("success", "updateWishStatus", "null");
        } catch(Exception e) {
            e.printStackTrace();
            return new Response("fail", "updateWishStatus", -1);
        }
    }
}
