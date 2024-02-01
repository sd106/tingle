package com.example.tingle.star.service.impl;

import com.example.tingle.star.dto.response.ReadStarByCategory;
import com.example.tingle.star.entity.StarEntity;
import com.example.tingle.star.repository.StarRepository;
import com.example.tingle.star.service.StarService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public List<ReadStarByCategory> findStarsByCategory(int category) {
        return starRepository.findStarsByCategory(category);
    }
}
