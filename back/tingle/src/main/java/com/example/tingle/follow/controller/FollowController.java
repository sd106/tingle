package com.example.tingle.follow.controller;

import com.example.tingle.follow.dto.response.FollowResponse;
import com.example.tingle.follow.entity.Follow;
import com.example.tingle.follow.service.FollowServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Tag(name = "팔로우 API")
@RequiredArgsConstructor
@Slf4j
public class FollowController {

    private final FollowServiceImpl followService;


    @Operation(summary = "팔로우한 모든 스타들 정보 조회")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{userId}/following")
    public FollowResponse getFollowing(@PathVariable Long userId) throws Exception {
        return new FollowResponse("성공", "팔로잉한 모든 스타들 리턴", followService.getFollowList(userId));

    }

}
