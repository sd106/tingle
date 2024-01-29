package com.example.tingle.wish.service.serviceImpl;

import com.example.tingle.user.repository.UserRepository;
import com.example.tingle.wish.entity.WishEntity;
import com.example.tingle.wish.repository.WishRepository;
import com.example.tingle.wish.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishServiceImpl implements WishService {

    @Autowired
    private WishRepository wishRepository;

    @Override
    public List<WishEntity> getAllWishes() {
        return wishRepository.findAll();
    }

    @Override
    public Optional<WishEntity> getWishById(Long id) {
        return wishRepository.findById(id);
    }

    @Override
    public WishEntity createWish(WishEntity wishEntity) {
        return wishRepository.save(wishEntity);
    }

    @Override
    public WishEntity updateWish(Long id, WishEntity newWish) {
        if (wishRepository.existsById(id)) {
            newWish.builder()
                    .id(id);
            return wishRepository.save(newWish);
        }
        return null; // or throw an exception
    }

    @Override
    public void deleteWish(Long id) {
        wishRepository.deleteById(id);
    }
}
