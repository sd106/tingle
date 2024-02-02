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
    private Long productId;
    private Long starId;
    private String starName;
    private String name;
    private int amount;
    private int price;
    private List<ProductImageDto> imageUrl;
    private String content;
    private boolean available;


    public static ProductDto convertToDto(ProductEntity productEntity) {
        ProductDto productDto = new ProductDto();
        productDto.setProductId(productEntity.getId());
        productDto.setStarId(productEntity.getStarId().getId()); // StarEntity의 ID를 가져와 설정
        productDto.setStarName(productEntity.getStarName());
        productDto.setName(productEntity.getName());
        productDto.setAmount(productEntity.getAmount());
        productDto.setPrice(productEntity.getPrice());
        productDto.setContent(productEntity.getContent());
        productDto.setAvailable(productEntity.isAvailable());

        // ProductImageEntity 리스트를 ProductImageDto 리스트로 변환
        List<ProductImageDto> imageDtos = productEntity.getImageUrl().stream()
                .map(imageEntity -> new ProductImageDto(imageEntity.getUrl(), imageEntity.getProduct().getId(), imageEntity.getId()))
                .collect(Collectors.toList());
        productDto.setImageUrl(imageDtos);

        return productDto;
    }
}
