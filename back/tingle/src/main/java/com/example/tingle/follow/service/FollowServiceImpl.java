package com.example.tingle.follow.service;


import com.example.tingle.follow.dto.FollowDto;
import com.example.tingle.follow.dto.request.FollowReadRequest;
import com.example.tingle.follow.entity.Follow;
import com.example.tingle.follow.repository.FollowRepository;
import com.example.tingle.user.entity.StarEntity;
import com.example.tingle.user.entity.UserEntity;
import com.example.tingle.user.repository.StarRepository;
import com.example.tingle.user.repository.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService{

    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final StarRepository starRepository;


    @Transactional(readOnly = true)
    @Override
    public ArrayList<FollowReadRequest> getFollowList(Long userId) throws Exception {

        List<Follow> follows= followRepository.findByUserEntityId(userId);
        ArrayList<FollowReadRequest> followReadRequests = new ArrayList<>();
        follows.forEach(follow -> followReadRequests.add(FollowReadRequest.toDto(follow)));
        return followReadRequests;
    }

    @Transactional
    @Override
    public FollowDto insertFollow(Long userId, Long starId) throws Exception {

        FollowDto followDto= new FollowDto();
        followDto.setId(followDto.getId());
        followDto.setUserId(userId);
        followDto.setStarId(starId);

        return followDto;

    }
}
