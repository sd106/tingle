package com.example.tingle.user.entity;

import com.example.tingle.store.entity.OrderEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "users")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String username;
    private String password;

    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    //gpt
//    @JsonManagedReference
    @OneToMany(mappedBy = "fan")
    private List<OrderEntity> orders;
    //

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
}
