package com.example.tingle.snapshot.dto;

import com.example.tingle.user.entity.StarEntity;
import com.example.tingle.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class SnapShotDto {

    private String imageUrl;

    private String content;

    private List<String> tags;

    private UserEntity user;

    private StarEntity star;
}
