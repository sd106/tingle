package com.example.tingle.store.service.impl;

import com.example.tingle.store.entity.ProductImageEntity;
import com.example.tingle.store.repository.ProductImageRepository;
import com.example.tingle.store.service.ProductImageService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductImageRepository productImageRepository;

    @Autowired
    public ProductImageServiceImpl(ProductImageRepository productImageRepository) {
        this.productImageRepository = productImageRepository;
    }

    @Override
    @Transactional
    public void deleteById(Long imageId) {
        productImageRepository.deleteById(imageId);
    }

    @Override
    public ProductImageEntity save(ProductImageEntity productImageEntity) {
        ProductImageEntity save = productImageRepository.save(productImageEntity);
        return save;
    }

    @Override
    public void deleteAll(List<ProductImageEntity> productImageEntity) {
        productImageRepository.deleteAll(productImageEntity);
    }
}
