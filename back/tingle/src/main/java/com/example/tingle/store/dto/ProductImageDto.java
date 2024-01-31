package com.example.tingle.store.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductImageDto {
    private String url; // 이미지 URL
    private Long productId; // 연결된 제품의 ID (선택적)

    // 기본 생성자
    public ProductImageDto() {
    }

    // 모든 필드를 포함하는 생성자
    public ProductImageDto(String url, Long productId) {
        this.url = url;
        this.productId = productId;
    }
}
