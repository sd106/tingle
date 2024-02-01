package com.example.tingle.follow.entity;

import com.example.tingle.star.entity.StarEntity;
import com.example.tingle.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "follow")
public class FollowEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_entity_id", foreignKey = @ForeignKey(name = "fk_follow_user"))

    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "star_entity_id", foreignKey = @ForeignKey(name = "fk_follow_star"))
    private StarEntity starEntity;

}
