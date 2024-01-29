package com.example.tingle.wish.controller;

import com.example.tingle.wish.entity.WishEntity;
import com.example.tingle.wish.repository.WishRepository;
import com.example.tingle.wish.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/wish")
public class WishController {

    @Autowired
    private WishService wishService;

    @GetMapping("/wish/all")
    public List<WishEntity> getAllWishes() {
        return wishService.getAllWishes();
    }

    @GetMapping("/{id}")
    public Optional<WishEntity> getWishById(@PathVariable Long id) {
        return wishService.getWishById(id);
    }

    @PostMapping
    public WishEntity createWish(@RequestBody WishEntity wishEntity) {
        return wishService.createWish(wishEntity);
    }

    @PutMapping("/{id}")
    public WishEntity updateWish(@PathVariable Long id, @RequestBody WishEntity newWish) {
        return wishService.updateWish(id, newWish);
    }

    @DeleteMapping("/{id}")
    public void deleteWish(@PathVariable Long id) {
        wishService.deleteWish(id);
    }
}
