package com.example.tingle.wish.service;

import com.example.tingle.wish.entity.WishEntity;
import com.example.tingle.wish.repository.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public interface WishService {
    public List<WishEntity> getAllWishes();

    public Optional<WishEntity> getWishById(Long id);

    public WishEntity createWish(WishEntity wishEntity);

    public WishEntity updateWish(Long id, WishEntity newWish);

    public void deleteWish(Long id);
}
