package com.example.tingle.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "stars")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    private String password;
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // OAuth2 로그인 때 구분한 Provider
    private String provider;

    @Column(nullable = false)
    private String email;

    public StarEntity update(String name, String picture) {
        this.username = name;
        this.picture = picture;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}