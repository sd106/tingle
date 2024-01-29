package com.example.tingle.follow.controller;

import com.example.tingle.follow.dto.request.FollowCreateRequest;
import com.example.tingle.follow.dto.response.FollowCreateResponse;
import com.example.tingle.follow.dto.response.FollowReadResponse;
import com.example.tingle.follow.entity.Follow;
import com.example.tingle.follow.service.FollowServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "팔로우 API")
@RequiredArgsConstructor
@Slf4j
public class FollowController {

    private final FollowServiceImpl followService;


    @Operation(summary = "팔로우한 모든 스타들 정보 조회")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/follow/{userId}")
    public FollowReadResponse getFollowing(@PathVariable Long userId) throws Exception {
        return new FollowReadResponse("성공", "팔로잉한 모든 스타들 리턴", followService.getFollowList(userId));

    }

    @PostMapping("/follow/following")
    public FollowCreateResponse createFollowing(@RequestBody FollowCreateRequest followCreateRequest){

        try {
            return new FollowCreateResponse("성공", "스타 팔로잉 성공", followService.insertFollow(followCreateRequest.getUserId(), followCreateRequest.getStarId()));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }

    }

}
