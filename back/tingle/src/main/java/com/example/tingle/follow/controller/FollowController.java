package com.example.tingle.follow.controller;

import com.example.tingle.follow.dto.FollowDto;
import com.example.tingle.follow.dto.event.FollowerAddedEvent;
import com.example.tingle.follow.dto.event.FollowerRemovedEvent;
import com.example.tingle.follow.dto.request.FollowCreateRequest;
import com.example.tingle.follow.dto.request.FollowReadRequest;
import com.example.tingle.follow.dto.response.Response;
import com.example.tingle.follow.service.FollowServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
@RestController
@Tag(name = "팔로우 API")
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/follow")
public class FollowController {

    private final FollowServiceImpl followService;
    private final ApplicationEventPublisher eventPublisher;

    @Operation(summary = "팔로우한 모든 스타들 정보 조회")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{userId}")
    public Response getFollowing(@PathVariable Long userId) throws Exception {
        return new Response("성공", "팔로잉한 모든 스타들 리턴", followService.getFollowList(userId));
    }

    @Operation(summary = "스타 팔로잉하기")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public Response createFollowing(@RequestBody FollowCreateRequest followCreateRequest){
        try {
            eventPublisher.publishEvent(new FollowerAddedEvent(this, followCreateRequest.getStarId()));
            return new Response("성공", "스타 팔로잉 성공",  followService.insertFollow(followCreateRequest.getUserId(), followCreateRequest.getStarId()));

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    @Operation(summary = "팔로잉 끊기")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delete")
    public Response deleteFolloing(@RequestBody FollowCreateRequest followCreateRequest) throws Exception {
        log.info("왓따왓따1");
        eventPublisher.publishEvent(new FollowerRemovedEvent(this, followCreateRequest.getStarId()));
        log.info("왓따왓따");
        return new Response("성공", "팔로잉 삭제",followService.deleteFollow(followCreateRequest.getUserId(), followCreateRequest.getStarId()));

    }

    @Operation(summary = "팔로잉 유무 조회")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/isFollowing")
    public Response getIsFollowing(@RequestParam Long userId, @RequestParam Long starId) throws Exception {
        return new Response("성공", "팔로잉 유무 리턴", followService.IsFollowing(userId, starId));

    }


    @Operation(summary = "가장 팔로워가 많이 증가한 스타 10명 조회")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/hotStars")
    public Response getHotStars() {

        return new Response("성공", "최근 팔로워가 많이 증가한 스타들", followService.getHotStarsInfo());

    }

}
