package com.example.tingle.star.service;

import com.example.tingle.star.entity.StarEntity;
import com.example.tingle.star.repository.StarRepository;
import com.example.tingle.star.service.StarService;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<StarEntity> findStarsByCategory(int category) {
        return starRepository.findStarsByCategory(category);
    }


}
