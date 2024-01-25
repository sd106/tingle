package com.example.tingle.store.service;

import com.example.tingle.store.dto.OrderDto;
import com.example.tingle.store.dto.ProductDto;
import com.example.tingle.store.entity.OrderEntity;
import com.example.tingle.store.entity.ProductEntity;
import com.example.tingle.store.repository.OrderRepository;
import com.example.tingle.store.repository.ProductRepository;
import com.example.tingle.user.entity.StarEntity;
import com.example.tingle.user.entity.UserEntity;
import com.example.tingle.user.repository.StarRepository;
import com.example.tingle.user.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Struct;
import java.util.List;
import java.util.Optional;

@Service
public class OrderProcess {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final StarRepository starRepository;

    @Autowired
    public OrderProcess(ProductRepository productRepository,
                        OrderRepository orderRepository,
                        UserRepository userRepository,
                        StarRepository starRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.starRepository = starRepository;
    }

    public boolean processOrder(Long productId, Integer fanId) {
        // 주문 프로세스 구현
        // 예를 들어, 상품 재고 감소, 주문 정보 저장 등의 작업을 수행할 수 있습니다.

        // 상품 재고 감소
        if (decreaseProductStock(productId)) {
            saveOrderInformation(productId, fanId);
            return true;
        }
        return false;
        // 주문 정보 저장
    }

    private boolean decreaseProductStock(Long productId) {
        // 상품 재고 감소 로직을 구현
        // 예를 들어, productRepository를 사용하여 상품의 재고를 감소시킬 수 있습니다.
        ProductEntity product = productRepository.findById(productId).orElse(null);

        if (product != null) {
            // 상품 재고가 충분한 경우에만 감소
            if (product.getAmount() > 0) {
                product.setAmount(product.getAmount() - 1);
                productRepository.save(product);
                return true;
            }
        }
        return false;
    }


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

    private void saveOrderInformation(Long productId, Integer fanId) {
        // 주문 정보 저장 로직을 구현
        // 예를 들어, 주문 정보를 데이터베이스에 저장하거나 다른 서비스로 전송할 수 있습니다.
        // 여기서는 단순히 주문 정보를 OrderRepository를 통해 저장하는 예시를 보여줍니다.
        OrderDto orderDto = new OrderDto(null, null);

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
}
