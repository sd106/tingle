package com.example.tingle.store.entity;


import com.example.tingle.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Orders")  // uniqueConstraints 속성에 아무 값도 설정하지 않음
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity fan;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity goods;

    @PostPersist
    public void setOrderIdAfterPersist() {
        this.orderId = this.Id;
    }
}