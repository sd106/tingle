package com.example.tingle.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserEntity is a Querydsl query type for UserEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserEntity extends EntityPathBase<UserEntity> {

    private static final long serialVersionUID = 2066598892L;

    public static final QUserEntity userEntity = new QUserEntity("userEntity");

    public final StringPath email = createString("email");

    public final SetPath<com.example.tingle.follow.entity.FollowEntity, com.example.tingle.follow.entity.QFollowEntity> followingStars = this.<com.example.tingle.follow.entity.FollowEntity, com.example.tingle.follow.entity.QFollowEntity>createSet("followingStars", com.example.tingle.follow.entity.FollowEntity.class, com.example.tingle.follow.entity.QFollowEntity.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<com.example.tingle.store.entity.OrderEntity, com.example.tingle.store.entity.QOrderEntity> orders = this.<com.example.tingle.store.entity.OrderEntity, com.example.tingle.store.entity.QOrderEntity>createList("orders", com.example.tingle.store.entity.OrderEntity.class, com.example.tingle.store.entity.QOrderEntity.class, PathInits.DIRECT2);

    public final StringPath password = createString("password");

    public final StringPath picture = createString("picture");

    public final StringPath provider = createString("provider");

    public final EnumPath<Role> role = createEnum("role", Role.class);

    public final StringPath username = createString("username");

    public QUserEntity(String variable) {
        super(UserEntity.class, forVariable(variable));
    }

    public QUserEntity(Path<? extends UserEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserEntity(PathMetadata metadata) {
        super(UserEntity.class, metadata);
    }

}

