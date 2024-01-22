package com.example.tingle.star.entity;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "star")
@Entity
@Data
public class Star {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 16)
    private String password;

    @Column(length = 10)
    private String name;

    @Column(length = 320)
    private String email;

    private String oauth_provider;

    private int profile;

    private int type;

    @Column(length = 10)
    private String nickName;

    @Column(length = 500)
    private String greetings;




}
