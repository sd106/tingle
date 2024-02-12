package com.example.tingle.store.service;

import com.example.tingle.store.entity.ProductImageEntity;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductImageService {

    @Transactional
    void deleteById(Long imageId);

    ProductImageEntity save(ProductImageEntity productImageEntity);

    void deleteAll(List<ProductImageEntity> productImageEntity);

}
