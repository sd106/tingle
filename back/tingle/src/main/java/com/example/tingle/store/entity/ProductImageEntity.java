package com.example.tingle.store.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ProductImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    public ProductImageEntity() {
        // 기본 생성자
    }

    public ProductImageEntity(String url, ProductEntity product) {
        this.url = url;
        this.product = product;
    }

    // Getter, Setter, 기타 메소드
}
