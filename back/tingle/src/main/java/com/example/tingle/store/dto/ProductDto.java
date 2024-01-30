package com.example.tingle.store.dto;

import com.example.tingle.store.entity.ProductEntity;
import com.example.tingle.store.entity.ProductImageEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ProductDto {
    private Long starId;
    private String starName;
    private String name;
    private int amount;
    private int price;
    private List<String> imageUrl;
    private String content;
    private boolean available;


    public static ProductDto convertToDto(ProductEntity productEntity) {
        List<String> imageUrls = productEntity.getImageUrl().stream()
                .map(ProductImageEntity::getUrl) // ProductImageEntity의 URL 추출
                .collect(Collectors.toList());
        ProductDto productDto = new ProductDto();
        productDto.setStarId(productEntity.getStarId().getId()); // StarEntity의 ID를 가져와 설정
        productDto.setStarName(productEntity.getStarName());
        productDto.setName(productEntity.getName());
        productDto.setAmount(productEntity.getAmount());
        productDto.setPrice(productEntity.getPrice());
        productDto.setImageUrl(imageUrls);
        productDto.setContent(productEntity.getContent());
        productDto.setAvailable(productEntity.isAvailable());

        return productDto;
    }
}
