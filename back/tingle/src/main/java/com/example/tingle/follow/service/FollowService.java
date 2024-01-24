package com.example.tingle.follow.service;

import com.example.tingle.follow.dto.request.FollowRequest;

import java.util.ArrayList;
import java.util.List;

public interface FollowService {

    ArrayList<FollowRequest> getFollowList(Long userId) throws Exception;

}
