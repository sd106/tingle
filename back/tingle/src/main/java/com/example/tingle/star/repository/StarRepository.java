package com.example.tingle.star.repository;

import com.example.tingle.star.entity.StarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StarRepository extends JpaRepository<StarEntity, Long>, CustomStarRepository {

    boolean existsByUsername(String username);

    StarEntity findByUsername(String username);

    /**
     *  소셜 로그인으로 반환되는 값 중 email을 통해
     *  이미 생성된 사용자인지 처음 가입한 사용자인지 판단하는 메서드
     */
    Optional<StarEntity> findByEmail(String email);

    Optional<StarEntity> findStarEntityById(Long starId);

    List<StarEntity> findByUsernameContaining(String starname);

    Optional<StarEntity> findByEmailAndProvider(String email, String provider);
}
