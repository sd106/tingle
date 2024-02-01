package com.example.tingle.wish.service.serviceImpl;

import com.amazonaws.services.kms.model.NotFoundException;
import com.example.tingle.user.entity.StarEntity;
import com.example.tingle.user.entity.UserEntity;
import com.example.tingle.user.repository.StarRepository;
import com.example.tingle.user.repository.UserRepository;
import com.example.tingle.wish.dto.WishDto;
import com.example.tingle.wish.dto.request.WishRequest;
import com.example.tingle.wish.entity.WishEntity;
import com.example.tingle.wish.repository.LikesRepository;
import com.example.tingle.wish.repository.WishRepository;
import com.example.tingle.wish.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public List<WishDto> readWithLikes(Long starId) {
        List<WishEntity> wishEntities = wishRepository.findByStarIdWithLikes(starId);
//        if (wishEntities.isEmpty()) { throw new NoSuchElementException("List is empty"); }

        List<WishDto> wishDtos = new ArrayList<>();

        for(WishEntity entity : wishEntities) {
            WishDto wishDto = WishDto.builder()
                    .id(entity.getId())
                    .userId(entity.getUser().getId())
                    .starId(entity.getStar().getId())
                    .status(entity.getStatus())
                    .points(entity.getPoints())
                    .likedCount(entity.getLikedCount())
                    .contents(entity.getContents())
//                    .createTime(entity.getCreateTime())
//                    .deleteTime(entity.getDeleteTime())
                    .build();

            wishDtos.add(wishDto);
        }
        return wishDtos;
    }

    public List<WishDto> readWithPoints(Long starId) {
        List<WishEntity> wishEntities = wishRepository.findByStarIdWithPoints(starId);
//        if (wishEntities.isEmpty()) { throw new NoSuchElementException("List is empty"); }

        List<WishDto> wishDtos = new ArrayList<>();

        for(WishEntity entity : wishEntities) {
            WishDto wishDto = WishDto.builder()
                    .id(entity.getId())
                    .userId(entity.getUser().getId())
                    .starId(entity.getStar().getId())
                    .status(entity.getStatus())
                    .points(entity.getPoints())
                    .likedCount(entity.getLikedCount())
                    .contents(entity.getContents())
//                    .createTime(entity.getCreateTime())
//                    .deleteTime(entity.getDeleteTime())
                    .build();

            wishDtos.add(wishDto);
        }
        return wishDtos;
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
                .status(wishRequest.getStatus())
                .points(wishRequest.getPoints())
                .likedCount(wishRequest.getLikedCount())
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
                .status(wishRequest.getStatus())
                .points(wishRequest.getPoints())
                .likedCount(wishRequest.getLikedCount())
                .contents(wishRequest.getContents())
//                .createTime(wishRequest.getCreateTime())
//                .deleteTime(wishRequest.getDeleteTime())
                .build();

        wishRepository.save(wishEntity);
    }

    @Override
    public void deleteWish(Long wishId) {
        WishEntity wish = wishRepository.findById(wishId)
                .orElseThrow(() -> new NotFoundException("Could not found wish id : " + wishId));

        wishRepository.deleteById(wish.getId());
    }

    @Override
    public void addPoints(Long wishId, int userId, int points) {
        WishEntity wish = wishRepository.findById(wishId)
                .orElseThrow(() -> new NotFoundException("Could not found wish id : " + wishId));

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Could not found user id : " + userId));

        // 포인트 차감 부분

        wishRepository.updatePointsByWishId(wish.getId(), points);
    }

    @Override
    public void updateWishStatus(Long wishId, int wishStatus) {
        WishEntity wish = wishRepository.findById(wishId)
                .orElseThrow(() -> new NotFoundException("Could not found wish id : " + wishId));

        wishRepository.updateStatusByWishId(wish.getId(), wishStatus);
    }
}
