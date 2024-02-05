package com.example.tingle.store.service.impl;

import com.example.tingle.store.dto.OrderDto;
import com.example.tingle.store.entity.OrderEntity;
import com.example.tingle.store.entity.ProductEntity;
import com.example.tingle.store.repository.OrderRepository;
import com.example.tingle.store.repository.ProductRepository;
import com.example.tingle.store.service.OrderService;
import com.example.tingle.star.entity.StarEntity;
import com.example.tingle.user.entity.UserEntity;
import com.example.tingle.star.repository.StarRepository;
import com.example.tingle.user.repository.UserRepository;
import com.example.tingle.user.service.impl.UserServiceImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final StarRepository starRepository;
    private final UserServiceImpl userService;
    private final ProductServiceImpl productService;
    private final EntityManager entityManager;

    @Autowired
    public OrderServiceImpl(ProductRepository productRepository,
                            OrderRepository orderRepository,
                            UserRepository userRepository,
                            StarRepository starRepository,
                            UserServiceImpl userService,
                            ProductServiceImpl productService,
                            EntityManager entityManager) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.starRepository = starRepository;
        this.userService = userService;
        this.productService = productService;
        this.entityManager = entityManager;
    }

    @Override
    public boolean processOrder(Long productId, Long fanId, String starName) {
        if (decreaseProductStock(productId)) {
            saveOrderInformation(productId, fanId);
            return true;
        }
        return false;
    }

    private boolean decreaseProductStock(Long productId) {
        Optional<ProductEntity> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            ProductEntity product = productOptional.get();
            if (product.getAmount() > 0) {
                product.setAmount(product.getAmount() - 1);
                productRepository.save(product);
                return true;
            }
        }
        return false;
    }

    private void saveOrderInformation(Long productId, Long fanId) {
        // 주문 정보 저장 로직을 구현
        // 예를 들어, 주문 정보를 데이터베이스에 저장하거나 다른 서비스로 전송할 수 있습니다.
        // 여기서는 단순히 주문 정보를 OrderRepository를 통해 저장하는 예시를 보여줍니다.
        Optional<UserEntity> fan = userRepository.findById(fanId);
        Optional<ProductEntity> product = productRepository.findById(productId);

        StarEntity star = product.get().getStarId();

        OrderEntity orderEntity = OrderEntity.builder()
                .goods(product.get())
                .fan(fan.get())
                .build();

        star.addOrderEntity(orderEntity);
        orderRepository.save(orderEntity);
    }

    @Override
    public void deleteOrderFromStar(Long starId, Long orderId) {
        Optional<StarEntity> optionalStarEntity = starRepository.findById(starId);
        if (optionalStarEntity.isPresent()) {
            StarEntity starEntity = optionalStarEntity.get();
            List<OrderEntity> orderEntities = starEntity.getOrderEntities();

            // 주문 목록에서 해당 주문을 찾아 제거
            orderEntities.removeIf(order -> order.getId().equals(orderId));

            // 변경사항 저장
            starRepository.save(starEntity);
        } else {
            System.out.println("i can't find star ---- OrderProcess");
        }
    }


    public OrderDto convertToDto(OrderEntity order) {
        OrderDto dto = new OrderDto();

        dto.setOrderId(order.getOrderId());
        dto.setFanId(order.getFan().getId());
        dto.setFan(userService.mapToDTO(order.getFan()));
        dto.setGoods(productService.mapToDTO(order.getGoods()));
        return dto;
    }

    @Override
    public Optional<OrderEntity> findById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    @Override
    public void deleteById(Long orderId) {
        orderRepository.deleteById(orderId);
    }


    public double processOrderById(Long orderId) {
        Optional<OrderEntity> order = orderRepository.findById(orderId);

        return order.map(orderEntity -> orderEntity.getGoods().getPrice()).orElse(0);
    }
}
