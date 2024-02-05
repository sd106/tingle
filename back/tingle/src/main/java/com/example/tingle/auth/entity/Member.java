package com.example.tingle.auth.entity;

import com.example.tingle.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "member")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String socialType;
    private String socialId;

    private Boolean isRealMember;

    private String name; // 진짜 이름
    @Column(unique = true)
    private String username; // 닉네임
    private String password;

    public void realMemeber(){
        this.isRealMember = Boolean.TRUE;
    }
}
