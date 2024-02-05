package com.example.tingle.auth.repository;

import com.example.tingle.auth.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findBySocialIdAndSocialType(String socialId, String socialType);
    Optional<Member> findByEmail(String email);
    boolean existsByUsername(String username);

    Optional<Member> findBySocialId(String socialId);
}
