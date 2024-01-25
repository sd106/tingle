package com.example.tingle.store.repository;

import com.example.tingle.store.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    // 다른 필요한 메서드들을 선언할 수 있음

    // 주문 정보 저장 메서드
}
