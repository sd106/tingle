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

    // 해당 스타의 위시 조회 (신규순,추천순,미션금순 / 미채택,채택,완료)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/read/{starId}/{sorting}/{status}")
    public Response readWishesWith(@PathVariable Long starId, @PathVariable int sorting,
                                   @PathVariable int status) {

        try {
            List<WishDto> list = wishService.readWishes(starId, sorting, status);

            if(list.isEmpty())
                return new Response("success", "readWishWithLikes", list);
            else
                return new Response("success", "readWishWithLikes", list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response("fail", "readWishWithLikes", null);
        }
    }

    // 위시 등록
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/save")
    public Response saveWish(@RequestBody WishRequest wishRequest) {

        try {
            wishService.saveWish(wishRequest);
            return new Response("success", "saveWish", null);
        } catch(Exception e) {
            e.printStackTrace();
            return new Response("fail", "saveWish", null);
        }
    }

    // 해당 위시 수정 (미구현 상태)
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/update")
    public Response updateWish(@RequestBody WishRequest wishRequest) {

        try {
            wishService.updateWish(wishRequest);
            return new Response("success", "updateWish", null);
        } catch(Exception e) {
            e.printStackTrace();
            return new Response("fail", "updateWish", null);
        }
    }

    // 해당 위시 삭제
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delete/{wishId}/{starId}/{userId}")
    public Response deleteWish(@PathVariable Long wishId, @PathVariable Long starId, @PathVariable Long userId) {

        try {
            wishService.deleteWish(wishId, starId, userId);
            return new Response("success", "deleteWish", null);
        } catch(Exception e) {
            e.printStackTrace();
            return new Response("fail", "deleteWish", null);
        }
    }

    // 해당 위시에 미션금 추가
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/add/points/{wishId}/{userId}/{points}")
    public Response addPoints(@PathVariable Long wishId, @PathVariable Long userId, @PathVariable int points) {

        try {
            wishService.addPoints(wishId, userId, points);
            return new Response("success", "addPoints", null);
        } catch(Exception e) {
            e.printStackTrace();
            return new Response("fail", "addPoints", null);
        }
    }

    // 해당 위시를 미채택/채택 상태로 변경 (스타)
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/update/status/star/{starId}/{wishId}/{wishStatus}")
    public Response updateWishStatusByStarId(@PathVariable Long starId, @PathVariable Long wishId, @PathVariable int wishStatus) {

        try {
            wishService.updateWishStatusByStarId(starId, wishId, wishStatus);
            return new Response("success", "updateWishStatus", null);
        } catch(Exception e) {
            e.printStackTrace();
            return new Response("fail", "updateWishStatus", null);
        }
    }

    // 해당 위시를 완료 상태로 변경 (팬)
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/update/status/user/{wishId}")
    public Response updateWishStatusByUserId(@PathVariable Long userId, @PathVariable Long wishId) {

        try {
            wishService.updateWishStatusByUserId(userId, wishId);
            return new Response("success", "updateWishStatus", null);
        } catch(Exception e) {
            e.printStackTrace();
            return new Response("fail", "updateWishStatus", null);
        }
    }
}
