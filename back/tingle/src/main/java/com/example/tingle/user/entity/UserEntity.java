package com.example.tingle.user.entity;

import com.example.tingle.follow.entity.FollowEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "users")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
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

    public UserEntity update(String name, String picture) {
        this.username = name;
        this.picture = picture;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

    @OneToMany(mappedBy = "userEntity")
    private Set<FollowEntity> followingStars = new HashSet<>();


}
