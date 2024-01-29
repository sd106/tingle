package com.example.tingle.follow.service;

import com.example.tingle.follow.dto.FollowDto;
import com.example.tingle.follow.dto.request.FollowReadRequest;
import com.example.tingle.follow.entity.Follow;

import java.util.ArrayList;

public interface FollowService {

    ArrayList<FollowReadRequest> getFollowList(Long userId) throws Exception;

    Follow insertFollow(Long userId, Long starId) throws Exception;

    boolean deleteFollow(Long userId, Long starId) throws Exception;

}
