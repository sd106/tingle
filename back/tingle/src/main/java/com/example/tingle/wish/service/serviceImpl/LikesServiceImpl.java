package com.example.tingle.wish.service.serviceImpl;

import com.amazonaws.services.kms.model.NotFoundException;
import com.example.tingle.user.repository.StarRepository;
import com.example.tingle.user.repository.UserRepository;
import com.example.tingle.wish.dto.response.WishResponse;
import com.example.tingle.wish.entity.LikesEntity;
import com.example.tingle.wish.entity.WishEntity;
import com.example.tingle.wish.repository.LikesRepository;
import com.example.tingle.wish.repository.WishRepository;
import com.example.tingle.wish.service.LikesService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class LikesServiceImpl implements LikesService {
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private StarRepository starRepository;
//    @Autowired
//    private WishRepository wishRepository;
//    @Autowired
//    private LikesRepository likesRepository;
//
//    // 해당 위시에 대한 좋아요 여부 조회
//    public boolean isLikes(Long wishId, int userId) {
//        boolean status = likesRepository.
////        if (wishEntities.isEmpty()) { throw new NoSuchElementException("List is empty"); }
//
//        List<WishResponse> wishResponses = new ArrayList<>();
//
//        for(WishEntity entity : wishEntities) {
//            WishResponse wishResponse = WishResponse.builder()
//                    .id(entity.getId())
//                    .userId(entity.getUser().getId())
//                    .starId(entity.getStar().getId())
//                    .status(entity.isStatus())
//                    .points(entity.getPoints())
//                    .liked(entity.getLiked())
//                    .contents(entity.getContents())
////                    .createTime(entity.getCreateTime())
////                    .deleteTime(entity.getDeleteTime())
//                    .build();
//
//            wishResponses.add(wishResponse);
//        }
//        return wishResponses;
//    }
//
//    // 해당 위시에 대한 좋아요 등록/취소
//    public boolean updateLikes(Long wishId, int userId) {
//        LikesEntity likesEntity = likesRepository.findByWishId(wishId, userId);
//
//        for(WishEntity entity : wishEntities) {
//            WishResponse wishResponse = WishResponse.builder()
//                    .id(entity.getId())
//                    .userId(entity.getUser().getId())
//                    .starId(entity.getStar().getId())
//                    .status(entity.isStatus())
//                    .points(entity.getPoints())
//                    .liked(entity.getLiked())
//                    .contents(entity.getContents())
////                    .createTime(entity.getCreateTime())
////                    .deleteTime(entity.getDeleteTime())
//                    .build();
//
//            wishResponses.add(wishResponse);
//        }
//        return wishResponses;
//    }
//
//    // 해당 위시에 대한 모든 좋아요 수 조회
//    public int getLikesCountByWishId(Long wishId) {
//        int count = likesRepository.getLikesCountByWishId(wishId);
//
//        return count;
//    }
}
