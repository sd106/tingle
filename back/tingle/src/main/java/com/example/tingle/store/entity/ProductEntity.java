package com.example.tingle.store.entity;

import com.example.tingle.star.entity.StarEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Goods")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    private StarEntity starId;

    private String starName;

    private String name;

    private int amount;

    private int price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImageEntity> imageUrl;

    private String content;

    private boolean available;

    // 주문 목록을 모두 처리하기 전까진 삭제 불가능하고 판매중, 판매 종료로만 나눌 수 있게 구현해야 함
    @OneToMany(mappedBy = "goods", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<OrderEntity> orders = new ArrayList<>();

    public void setId(Long id) {
        this.Id = id;
    }

    public void setStarId(StarEntity starId) {
        this.starId = starId;
    }

    public void setStarName(String starName) {
        this.starName = starName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
    // Getter, Setter, Constructors, and other methods
    public void setImageUrl(List<ProductImageEntity> imageUrl) {
        if (this.imageUrl == null) {
            this.imageUrl = imageUrl;
        } else {
            this.imageUrl.clear(); // 기존 리스트를 클리어하고
            this.imageUrl.addAll(imageUrl); // 새로운 리스트의 모든 아이템을 추가
        }
    }

    // 이미지를 추가하는 메서드
    public void addImage(ProductImageEntity image) {
        if (this.imageUrl == null) {
            this.imageUrl = new ArrayList<>();
        }
        this.imageUrl.add(image);
        image.setProduct(this);
    }

    // 이미지를 제거하는 메서드
    public void removeImage(ProductImageEntity image) {
        if (this.imageUrl != null && this.imageUrl.remove(image)) {
            image.setProduct(null);
        }
    }
}
