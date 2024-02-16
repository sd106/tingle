package com.example.tingle.follow.service;


import com.example.tingle.follow.dto.event.FollowerAddedEvent;
import com.example.tingle.follow.dto.event.FollowerRemovedEvent;
import com.example.tingle.follow.dto.request.FollowReadRequest;
import com.example.tingle.follow.entity.FollowEntity;
import com.example.tingle.follow.repository.FollowRepository;
import com.example.tingle.star.dto.request.ReadStarRequest;
import com.example.tingle.star.entity.StarEntity;
import com.example.tingle.star.repository.StarRepository;
import com.example.tingle.user.entity.UserEntity;
import com.example.tingle.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FollowServiceImpl implements FollowService{

    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final StarRepository starRepository;

    private Map<Long, Integer> followerCountMap= new ConcurrentHashMap<Long, Integer>();
    private List<Map.Entry<Long, Integer>> hotStars;

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
    public boolean insertFollow(Long userId, Long starId) throws Exception {
        try {
            FollowEntity followEntity = new FollowEntity();
            followEntity.setUserEntity(userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("유저 엔티티를 찾을 수 없습니다.")));
            followEntity.setStarEntity(starRepository.findById(starId).orElseThrow(() -> new EntityNotFoundException("스타 엔티티를 찾을 수 없습니다.")));

            followRepository.save(followEntity);
            return true;
        } catch (Exception e){
            return false;
        }
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

    @EventListener
    public void handleFollowerAddedEvent(FollowerAddedEvent event) {
        incrementFollowerCount(event.getStarId());
    }

    @EventListener
    public void handleFollowerRemovedEvent(FollowerRemovedEvent event) {
        decrementFollowerCount(event.getStarId());
    }

    public void incrementFollowerCount(Long starId) {
        log.info("증가함");
        followerCountMap.put(starId, followerCountMap.getOrDefault(starId, 0) + 1);

    }

    public void decrementFollowerCount(Long starId) {
        log.info("감소함");
        followerCountMap.put(starId, followerCountMap.get(starId) - 1);
    }

    private List<ReadStarRequest> hotStarsInfo;

    //가장 구독자가 많이 오른 스타10명 을 계산함
    //@Scheduled(fixedDelay = 3600000)
    public void CalculHotStars() {

        hotStars = followerCountMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(10)
                .collect(Collectors.toList());

        // hotStars의 각 starId에 해당하는 FollowReadRequest를 리스트에 추가합니다.
        hotStarsInfo = new ArrayList<>();
        for (Map.Entry<Long, Integer> hotStar : hotStars) {
            Long starId = hotStar.getKey();
            ReadStarRequest readStarRequest = getFollow(starId);
            hotStarsInfo.add(readStarRequest);
        }

        // 원본 팔로워 수 정보를 초기화합니다.
        //followerCountMap.clear();
    }

    public List<ReadStarRequest> getHotStarsInfo() {
        CalculHotStars();
        return hotStarsInfo;
    }

    public ReadStarRequest getFollow(Long starId) {
        StarEntity starEntity = starRepository.findStarEntityById(starId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid starId: " + starId));
        return ReadStarRequest.toDto(starEntity);
    }

    @Override
    public boolean IsFollowing(Long userId, Long starId) {
        UserEntity userEntity=  userRepository.findById(userId)
                .orElseThrow(() ->new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        return userEntity.getFollowingStars().stream()
                .anyMatch(follow -> follow.getStarEntity()
                        .getId().equals(starId));

    }

}
