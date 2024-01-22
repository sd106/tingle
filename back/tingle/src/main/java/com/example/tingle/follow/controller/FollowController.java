package com.example.tingle.follow.controller;

import com.example.tingle.follow.repository.FollowRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FollowController {

    private final FollowRepositoryImpl followRepository;

    @GetMapping("/follow")
    public List<String> getQuery(){
        List<String> list = followRepository.getFollows(1);
        for(String s: list){
            System.out.println(s);
        }
        return list;
    }










}
