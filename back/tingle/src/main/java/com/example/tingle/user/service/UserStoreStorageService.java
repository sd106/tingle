package com.example.tingle.user.service;

import com.example.tingle.user.entity.UserStoreStorage;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserStoreStorageService {
    UserStoreStorage save(UserStoreStorage userStoreStorage);

    Optional<UserStoreStorage> findById(Long id);

    void deleteById(Long id);
}
