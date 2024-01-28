package com.example.tingle.store.repository;

import com.example.tingle.store.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// JpaRepository를 확장하도록 변경
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    // 예시: 특정 starId에 해당하는 Product 조회
//    List<ProductEntity> findByStarId(Long starId);
    void deleteById(Long productId);

    List<ProductEntity> findByStarName(String starName);
    // 추가적으로 필요한 메서드들을 선언할 수 있음
}