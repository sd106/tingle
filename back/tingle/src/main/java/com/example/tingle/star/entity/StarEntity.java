package com.example.tingle.star.entity;

import com.example.tingle.follow.entity.FollowEntity;
import com.example.tingle.home.dto.HomeProfileDto;
import com.example.tingle.home.entity.HomeEntity;
import com.example.tingle.store.entity.OrderEntity;
import com.example.tingle.user.entity.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity(name = "star")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(unique = true)
    private String username;
    private String password;

    @Setter
    private String picture;


    // OAuth2 로그인 때 구분한 Provider
    private String provider;
    private String providerId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private int category;

    private String snsUrl;

    private String banner;

    // Order된 목록 추가
//    이건 필요 없지 않을까??
//    @JsonManagedReference
    @OneToMany
//    @JsonIgnore
    @JoinColumn(name = "orders", nullable = true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private List<OrderEntity> orderEntities;


    public StarEntity update(String name, String picture) {
        this.username = name;
        this.picture = picture;
        return this;
    }


    public void addOrderEntity(OrderEntity orderEntity) {
        if (orderEntities == null) {
            orderEntities = new ArrayList<>();
        }
        
        orderEntities.add(orderEntity);
    }

    @OneToMany(mappedBy = "starEntity")
    private Set<FollowEntity> followerUsers;

    @OneToMany(mappedBy="starEntity")
    private List<HomeEntity> homes;

    public HomeProfileDto toDto() {
        return HomeProfileDto.builder()
                .banner(this.banner)
                .profileImage(this.picture)
                .username(this.username)
                .snsUrl(this.snsUrl)
                .build();
    }


}