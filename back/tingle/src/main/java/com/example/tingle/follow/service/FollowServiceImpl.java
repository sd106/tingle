package com.example.tingle.follow.service;


import com.amazonaws.transform.MapEntry;
import com.example.tingle.follow.dto.event.FollowerAddedEvent;
import com.example.tingle.follow.dto.event.FollowerRemovedEvent;
import com.example.tingle.follow.dto.request.FollowReadRequest;
import com.example.tingle.follow.entity.FollowEntity;
import com.example.tingle.follow.repository.FollowRepository;
import com.example.tingle.star.repository.StarRepository;
import com.example.tingle.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
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

    private List<FollowReadRequest> hotStarsInfo;

    //가장 구독자가 많이 오른 스타10명 을 계산함
    @Scheduled(fixedDelay = 60000)
    public void CalculHotStars() {

        hotStars = followerCountMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(10)
                .collect(Collectors.toList());

        // hotStars의 각 starId에 해당하는 FollowReadRequest를 리스트에 추가합니다.
        hotStarsInfo = new ArrayList<>();
        for (Map.Entry<Long, Integer> hotStar : hotStars) {
            Long starId = hotStar.getKey();
            FollowReadRequest followReadRequest = getFollow(starId);
            hotStarsInfo.add(followReadRequest);
        }

        for(FollowReadRequest x: hotStarsInfo){
            log.info(String.valueOf(x.getStarId()));
        }

        // 원본 팔로워 수 정보를 초기화합니다.
        followerCountMap.clear();
    }

    public List<FollowReadRequest> getHotStarsInfo() {
        return hotStarsInfo;
    }

    public FollowReadRequest getFollow(Long starId) {
        FollowEntity followEntity = followRepository.findByStarEntityId(starId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid starId: " + starId));
        return FollowReadRequest.toDto(followEntity);
    }

}
