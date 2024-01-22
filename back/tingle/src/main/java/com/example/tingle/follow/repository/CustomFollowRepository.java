package com.example.tingle.follow.repository;

import com.example.tingle.follow.dto.response.FollowResponse;
import com.example.tingle.follow.entity.Follow;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface CustomFollowRepository{

 List<String> getFollows(int userId);

}
