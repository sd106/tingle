package com.example.tingle.user.service;

import com.example.tingle.store.entity.ProductEntity;
import com.example.tingle.user.entity.StarEntity;

import java.util.List;
import java.util.Optional;

public interface StarService {
    StarEntity findByUsername(String username);
}
