package com.example.tingle.user.service.impl;

import com.example.tingle.user.entity.UserStoreStorage;
import com.example.tingle.user.repository.UserStoreStorageRepository;
import com.example.tingle.user.service.UserStoreStorageService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserStoreStorageServiceImpl implements UserStoreStorageService {

    private final UserStoreStorageRepository userStoreStorageRepository;

    public UserStoreStorageServiceImpl(UserStoreStorageRepository userStoreStorageRepository){
        this.userStoreStorageRepository = userStoreStorageRepository;
    }

    @Override
    public UserStoreStorage save(UserStoreStorage userStoreStorage) {
        return userStoreStorageRepository.save(userStoreStorage);
    }

    @Override
    public Optional<UserStoreStorage> findById(Long id) {
        return userStoreStorageRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        userStoreStorageRepository.deleteById(id);
    }

}
