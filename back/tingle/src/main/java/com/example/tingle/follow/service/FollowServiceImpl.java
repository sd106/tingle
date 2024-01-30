package com.example.tingle.follow.service;


import com.example.tingle.follow.dto.request.FollowReadRequest;
import com.example.tingle.follow.entity.FollowEntity;
import com.example.tingle.follow.repository.FollowRepository;
import com.example.tingle.user.repository.StarRepository;
import com.example.tingle.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FollowServiceImpl implements FollowService{

    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final StarRepository starRepository;


    @Transactional(readOnly = true)
    @Override
    public ArrayList<FollowReadRequest> getFollowList(Long userId) throws Exception {

        List<FollowEntity> followEntities = followRepository.findByUserEntityId(userId);

        ArrayList<FollowReadRequest> followReadRequests = new ArrayList<>();
        followEntities.forEach(follow -> followReadRequests.add(FollowReadRequest.toDto(follow)));
        return followReadRequests;
    }

    @Transactional
    @Override
    public FollowEntity insertFollow(Long userId, Long starId) throws Exception {

        FollowEntity followEntity = new FollowEntity();
        followEntity.setUserEntity(userRepository.findById(userId).orElseThrow(()-> new EntityNotFoundException("유저 엔티티를 찾을 수 없습니다.")));
        followEntity.setStarEntity(starRepository.findById(starId).orElseThrow(()-> new EntityNotFoundException("스타 엔티티를 찾을 수 없습니다.")));

        return followRepository.save(followEntity);

    }

    @Transactional
    @Override
    public boolean deleteFollow(Long userId, Long starId) throws Exception {

        FollowEntity followEntity = followRepository.findByUserEntityIdAndStarEntityId(userId, starId);
        if (followEntity == null) {
            throw new IllegalArgumentException("해당 사용자와 스타 사이의 팔로우가 존재하지 않습니다.");
        }

        followRepository.delete(followEntity);
        return true;
    }
}
