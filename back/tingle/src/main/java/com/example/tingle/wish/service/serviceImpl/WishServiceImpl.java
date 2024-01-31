package com.example.tingle.wish.service.serviceImpl;

import com.amazonaws.services.kms.model.NotFoundException;
import com.example.tingle.user.entity.StarEntity;
import com.example.tingle.user.entity.UserEntity;
import com.example.tingle.user.repository.StarRepository;
import com.example.tingle.user.repository.UserRepository;
import com.example.tingle.wish.dto.request.WishRequest;
import com.example.tingle.wish.dto.response.WishResponse;
import com.example.tingle.wish.entity.LikesEntity;
import com.example.tingle.wish.entity.WishEntity;
import com.example.tingle.wish.repository.LikesRepository;
import com.example.tingle.wish.repository.WishRepository;
import com.example.tingle.wish.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class WishServiceImpl implements WishService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StarRepository starRepository;
    @Autowired
    private WishRepository wishRepository;
    @Autowired
    private LikesRepository likesRepository;

    @Override
    public List<WishResponse> read(Long starId) {
        List<WishEntity> wishEntities = wishRepository.findByStarId(starId);
//        if (wishEntities.isEmpty()) { throw new NoSuchElementException("List is empty"); }

        List<WishResponse> wishResponses = new ArrayList<>();

        for(WishEntity entity : wishEntities) {
            WishResponse wishResponse = WishResponse.builder()
                    .id(entity.getId())
                    .userId(entity.getUser().getId())
                    .starId(entity.getStar().getId())
                    .status(entity.isStatus())
                    .points(entity.getPoints())
//                    .liked(entity.getLiked())
                    .contents(entity.getContents())
//                    .createTime(entity.getCreateTime())
//                    .deleteTime(entity.getDeleteTime())
                    .build();

            wishResponses.add(wishResponse);
        }
        return wishResponses;
    }

    @Override
    public void saveWish(WishRequest wishRequest) {
        UserEntity user = userRepository.findById(wishRequest.getUserId())
                .orElseThrow(() -> new NotFoundException("Could not found user id : " + wishRequest.getUserId()));

        StarEntity star = starRepository.findById(wishRequest.getStarId())
                .orElseThrow(() -> new NotFoundException("Could not found star id : " + wishRequest.getStarId()));

        WishEntity wishEntity = WishEntity.builder()
                .id(wishRequest.getId())
                .user(user)
                .star(star)
                .status(wishRequest.isStatus())
                .points(wishRequest.getPoints())
//                .liked(wishRequest.getLiked())
                .contents(wishRequest.getContents())
//                .createTime(wishRequest.getCreateTime())
//                .deleteTime(wishRequest.getDeleteTime())
                .build();

        wishRepository.save(wishEntity);
    }

    /* 수정 기능 미구현 */
    @Override
    public void updateWish(WishRequest wishRequest) {
        UserEntity user = userRepository.findById(wishRequest.getUserId())
                .orElseThrow(() -> new NotFoundException("Could not found user id : " + wishRequest.getUserId()));

        StarEntity star = starRepository.findById(wishRequest.getStarId())
                .orElseThrow(() -> new NotFoundException("Could not found star id : " + wishRequest.getStarId()));

        WishEntity wishEntity = WishEntity.builder()
                .id(wishRequest.getId())
                .user(user)
                .star(star)
                .status(wishRequest.isStatus())
                .points(wishRequest.getPoints())
//                .liked(wishRequest.getLiked())
                .contents(wishRequest.getContents())
//                .createTime(wishRequest.getCreateTime())
//                .deleteTime(wishRequest.getDeleteTime())
                .build();

        wishRepository.save(wishEntity);
    }

    @Override
    public void deleteWish(Long id) {
        wishRepository.deleteById(id);
    }
}
