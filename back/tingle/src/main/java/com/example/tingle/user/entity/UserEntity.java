package com.example.tingle.user.entity;

import com.example.tingle.follow.entity.FollowEntity;
import com.example.tingle.store.entity.OrderEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "user")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(unique = true)
    private String username;

    private String picture;


    //gpt
//    @JsonManagedReference
    @OneToMany(mappedBy = "fan")
    private List<OrderEntity> orders;
    //

    // OAuth2 로그인 때 구분한 Provider
    private String provider;
    private String providerId;

    @Column(nullable = false)
    private String email;

    public UserEntity update(String name, String picture) {
        this.username = name;
        this.picture = picture;
        return this;
    }

    @OneToMany(mappedBy = "userEntity")
    private Set<FollowEntity> followingStars = new HashSet<>();


}
