package com.example.tingle.star.service.impl;

import com.example.tingle.star.dto.request.ReadStarRequest;
import com.example.tingle.star.dto.response.ReadStarByCategory;
import com.example.tingle.star.entity.StarEntity;
import com.example.tingle.star.repository.StarRepository;
import com.example.tingle.star.service.StarService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Optional<StarEntity> findById(Long starId) {
        return starRepository.findById(starId);
    }

    @Override
    public List<ReadStarByCategory> findStarsByCategory(int category) {
        return starRepository.findStarsByCategory(category);
    }

    @Override
    public void save(StarEntity starEntity) {
        starRepository.save(starEntity);
    }
    public List<ReadStarRequest> find10Stars() {
       return starRepository.find10Stars();
    }

}
