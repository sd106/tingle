package com.example.tingle.follow.service;

import com.example.tingle.follow.dto.request.FollowRequest;
import com.example.tingle.follow.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService{

    private final FollowRepository followRepository;



}
