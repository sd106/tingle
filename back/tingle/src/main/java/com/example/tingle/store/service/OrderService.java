package com.example.tingle.store.service;

import com.example.tingle.store.entity.OrderEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface OrderService {

    boolean processOrder(Long productId, Long fanId, String starName);

    void deleteOrderFromStar(Long starId, Long orderId);
    Optional<OrderEntity> findById(Long orderId);

    void deleteById(Long orderId);


}
