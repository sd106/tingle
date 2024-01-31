package com.example.tingle.store.service;

import com.example.tingle.store.dto.ProductDto;
import com.example.tingle.store.entity.ProductEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ProductService {



    ProductDto mapToDTO(ProductEntity productEntity);
    ProductEntity convertDtoToEntity(ProductDto productDto);
    Optional<ProductEntity> findById(Long productId);

    void save(ProductEntity productEntity);

    void deleteById(Long productId);

    List<ProductEntity> findByStarName(String starName);

}
