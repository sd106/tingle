package com.example.tingle.user.service.impl;

import com.example.tingle.user.entity.StarEntity;
import com.example.tingle.user.repository.StarRepository;
import com.example.tingle.user.service.StarService;
import org.springframework.stereotype.Service;

@Service
public class StarServiceImpl implements StarService {

    private final StarRepository starRepository;

    public StarServiceImpl(StarRepository starRepository) {
        this.starRepository = starRepository;
    }

    @Override
    public StarEntity findByUsername(String username) {
        return starRepository.findByUsername(username);
    }

}
