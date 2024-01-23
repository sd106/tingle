package com.example.tingle.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    ADMIN("ROLE_ADMIN", "관리자"),
    FAN("ROLE_USER", "일반 사용자"),
    STAR("ROLE_STAR", "스타");

    private final String key;
    private final String title;
}