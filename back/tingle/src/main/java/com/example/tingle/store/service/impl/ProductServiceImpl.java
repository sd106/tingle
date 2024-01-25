package com.example.tingle.store.service.impl;

import com.example.tingle.store.dto.ProductDto;
import com.example.tingle.store.entity.ProductEntity;
import com.example.tingle.store.repository.ProductRepository;
import com.example.tingle.store.service.ProductService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public ProductDto mapToDTO(ProductEntity productEntity) {
        ProductDto productDTO = new ProductDto();
        productDTO.setName(productEntity.getName());
        productDTO.setAmount(productEntity.getAmount());
        productDTO.setPrice(productEntity.getPrice());
        productDTO.setImageUrl(productEntity.getImageUrl());
        productDTO.setContent(productEntity.getContent());
        productDTO.setAvailable(productEntity.isAvailable());
        return productDTO;
    }


    @Override
    public Optional<ProductEntity> findById(Long productId) {
        return productRepository.findById(productId);
    }

    @Override
    public void save(ProductEntity productEntity) {
        productRepository.save(productEntity);
    }

    @Override
    public void deleteById(Long productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public List<ProductEntity> findByStarName(String starName) {
        return productRepository.findByStarName(starName);
    }
}
