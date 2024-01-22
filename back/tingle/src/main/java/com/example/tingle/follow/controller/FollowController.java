package com.example.tingle.follow.controller;

import com.example.tingle.follow.repository.FollowRepositoryImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Tag(name = "팔로우 API")
@RequiredArgsConstructor
@Slf4j
public class FollowController {

    private final FollowRepositoryImpl followRepository;


    @Operation(summary = "유저가 팔로우한 스타목록 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "팔로우 스타 목록 조회 성공")
    })
    @GetMapping("/follow")
    public List<String> getQuery(){
        List<String> list = followRepository.getFollows(1);
        for(String s: list){
            System.out.println(s);
        }
        return list;
    }










}
