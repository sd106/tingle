package com.example.tingle.wish.service.serviceImpl;

import com.amazonaws.services.kms.model.NotFoundException;
import com.example.tingle.star.entity.StarEntity;
import com.example.tingle.star.repository.StarRepository;
import com.example.tingle.user.entity.UserEntity;
import com.example.tingle.user.repository.UserRepository;
import com.example.tingle.wish.dto.WishDto;
import com.example.tingle.wish.dto.request.WishRequest;
import com.example.tingle.wish.entity.WishEntity;
import com.example.tingle.wish.repository.WishRepository;
import com.example.tingle.wish.service.WishService;
import jakarta.transaction.Transactional;
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

    @Transactional
    @Override
    public List<WishDto> readWishes(Long starId, int sorting, int status) {
        List<WishEntity> wishEntities;

        if(sorting == 0) // 신규순
            wishEntities  = wishRepository.findByStarIdWithNews(starId, status);
        else if(sorting == 1)// 추천순
            wishEntities  = wishRepository.findByStarIdWithLikes(starId, status);
        else // 미션금순
            wishEntities  = wishRepository.findByStarIdWithPoints(starId, status);

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

    @Transactional
    @Override
    public void saveWish(WishRequest wishRequest) {
        UserEntity user = userRepository.findById(wishRequest.getUserId())
                .orElseThrow(() -> new NotFoundException("Could not found user id : " + wishRequest.getUserId()));

        StarEntity star = starRepository.findById(wishRequest.getStarId())
                .orElseThrow(() -> new NotFoundException("Could not found star id : " + wishRequest.getStarId()));

        WishEntity wishEntity = WishEntity.builder()
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
    @Transactional
    @Override
    public void updateWish(WishRequest wishRequest) {
        UserEntity user = userRepository.findById(wishRequest.getUserId())
                .orElseThrow(() -> new NotFoundException("Could not found user id : " + wishRequest.getUserId()));

        StarEntity star = starRepository.findById(wishRequest.getStarId())
                .orElseThrow(() -> new NotFoundException("Could not found star id : " + wishRequest.getStarId()));

        WishEntity wishEntity = WishEntity.builder()
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

    @Transactional
    @Override
    public void deleteWish(Long wishId, Long starId, Long userId) {
        WishEntity wish = wishRepository.findById(wishId)
                .orElseThrow(() -> new NotFoundException("Could not found wish id : " + wishId));
        StarEntity star = starRepository.findById(starId)
                .orElseThrow(() -> new NotFoundException("Could not found star id : " + starId));
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Could not found user id : " + userId));

        if(wish.getUser().getId() == user.getId() && wish.getStar().getId() == star.getId())
            wishRepository.deleteById(wish.getId());
    }

    @Transactional
    @Override
    public void addPoints(Long wishId, Long userId, int points) {
        WishEntity wish = wishRepository.findById(wishId)
                .orElseThrow(() -> new NotFoundException("Could not found wish id : " + wishId));

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Could not found user id : " + userId));

        // 포인트 차감 부분

        wishRepository.updatePointsByWishId(wish.getId(), points);
    }

    @Transactional
    @Override
    public void updateWishStatusByUserId(Long userId, Long wishId) {
        WishEntity wish = wishRepository.findById(wishId)
                .orElseThrow(() -> new NotFoundException("Could not found wish id : " + wishId));
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Could not found user id : " + userId));

        wishRepository.updateStatusByUserId(user.getId(), wish.getId());
    }

    @Transactional
    @Override
    public void updateWishStatusByStarId(Long starId, Long wishId, int wishStatus) {
        WishEntity wish = wishRepository.findById(wishId)
                .orElseThrow(() -> new NotFoundException("Could not found wish id : " + wishId));
        StarEntity star = starRepository.findById(starId)
                .orElseThrow(() -> new NotFoundException("Could not found star id : " + starId));

        wishRepository.updateStatusByStarId(star.getId(), wish.getId(), wishStatus);
    }
}
