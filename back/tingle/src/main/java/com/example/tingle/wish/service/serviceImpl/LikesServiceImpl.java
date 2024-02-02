package com.example.tingle.wish.service.serviceImpl;

import com.amazonaws.services.kms.model.NotFoundException;
import com.example.tingle.star.repository.StarRepository;
import com.example.tingle.user.entity.UserEntity;
import com.example.tingle.user.repository.UserRepository;
import com.example.tingle.wish.dto.LikesDto;
import com.example.tingle.wish.dto.WishDto;
import com.example.tingle.wish.entity.LikesEntity;
import com.example.tingle.wish.entity.WishEntity;
import com.example.tingle.wish.repository.LikesRepository;
import com.example.tingle.wish.repository.WishRepository;
import com.example.tingle.wish.service.LikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LikesServiceImpl implements LikesService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WishRepository wishRepository;
    @Autowired
    private LikesRepository likesRepository;

    @Override
    public List<LikesDto> readLikesList(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Could not found user id : " + userId));

        List<LikesEntity> likesEntities = likesRepository.findAllByUserId(user.getId());

        List<LikesDto> likesDtos = new ArrayList<>();

        for(LikesEntity entity : likesEntities) {
            LikesDto likesDto = LikesDto.builder()
                    .id(entity.getId())
                    .userId(entity.getUser().getId())
                    .wishId(entity.getWish().getId())
                    .liked(entity.isLiked())
                    .build();

            likesDtos.add(likesDto);
        }
        return likesDtos;
    }

    @Override
    public int isLikes(Long wishId, Long userId) {
        WishEntity wish = wishRepository.findById(wishId)
                .orElseThrow(() -> new NotFoundException("Could not found wish id : " + wishId));

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Could not found user id : " + userId));

        LikesEntity likes = likesRepository.findByIds(wish.getId(), user.getId());

        // 존재하지 않으면 false, 존재하지만 false 이면 false, 존재하고 true 이면 true 반환
        if(likes == null)
            return 0;
        else if (!likes.isLiked())
            return 1;
        else
            return 2;
    }

    @Override
    public int updateLikes(Long wishId, Long userId) {
        WishEntity wish = wishRepository.findById(wishId)
                .orElseThrow(() -> new NotFoundException("Could not found wish id : " + wishId));

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Could not found user id : " + userId));


        LikesEntity likes = likesRepository.findByIds(wish.getId(), user.getId());

        // 존재하지 않으면 create 후 0 반환, 존재하고 false/true 이면 토글하고 1/2 반환
        if(likes == null) {
            LikesEntity newLikes = LikesEntity.builder()
                    .liked(true)
                    .wish(wish)
                    .user(user)
                    .build();

            likesRepository.save(newLikes);
            wishRepository.updateLikedPlusByWishId(wish.getId());
            return 0;
        }
        else if (!likes.isLiked()) {
            likesRepository.updateLikedByWishId(wish.getId());
            wishRepository.updateLikedPlusByWishId(wish.getId());
            return 1;
        }
        else {
            likesRepository.updateLikedByWishId(wish.getId());
            wishRepository.updateLikedMinusByWishId(wish.getId());
            return 2;
        }
    }
}
