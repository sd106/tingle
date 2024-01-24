package com.example.tingle.follow.service;

import com.example.tingle.follow.dto.request.FollowRequest;
import com.example.tingle.follow.entity.Follow;
import com.example.tingle.follow.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService{

    private final FollowRepository followRepository;


    @Transactional(readOnly = true)
    @Override
    public ArrayList<FollowRequest> getFollowList(Long userId) throws Exception {

        List<Follow> follows= followRepository.findByUserEntityId
                (userId);
        ArrayList<FollowRequest> followRequests= new ArrayList<>();
        follows.forEach(follow -> followRequests.add(FollowRequest.toDto(follow)));
        return followRequests;
    }
}
