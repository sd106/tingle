package com.example.tingle.store.dto;

import com.example.tingle.store.entity.ProductEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductDto {
    private Long starId;
    private String starName;
    private String name;
    private int amount;
    private double price;
    private String imageUrl;
    private String content;
    private boolean available;


    public static ProductDto convertToDto(ProductEntity productEntity) {
        ProductDto productDto = new ProductDto();
        productDto.setStarId(productEntity.getStarId().getId()); // StarEntity의 ID를 가져와 설정
        productDto.setStarName(productEntity.getStarName());
        productDto.setName(productEntity.getName());
        productDto.setAmount(productEntity.getAmount());
        productDto.setPrice(productEntity.getPrice());
        productDto.setImageUrl(productEntity.getImageUrl());
        productDto.setContent(productEntity.getContent());
        productDto.setAvailable(productEntity.isAvailable());

        return productDto;
    }
}
