package com.example.tingle.store.service;

import com.example.tingle.store.dto.ProductDto;
import com.example.tingle.store.entity.ProductEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductService {


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

}
