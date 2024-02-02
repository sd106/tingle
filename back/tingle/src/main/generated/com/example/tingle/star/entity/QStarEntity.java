package com.example.tingle.star.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStarEntity is a Querydsl query type for StarEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStarEntity extends EntityPathBase<StarEntity> {

    private static final long serialVersionUID = -396309510L;

    public static final QStarEntity starEntity = new QStarEntity("starEntity");

    public final NumberPath<Integer> category = createNumber("category", Integer.class);

    public final StringPath email = createString("email");

    public final SetPath<com.example.tingle.follow.entity.FollowEntity, com.example.tingle.follow.entity.QFollowEntity> followerUsers = this.<com.example.tingle.follow.entity.FollowEntity, com.example.tingle.follow.entity.QFollowEntity>createSet("followerUsers", com.example.tingle.follow.entity.FollowEntity.class, com.example.tingle.follow.entity.QFollowEntity.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<com.example.tingle.store.entity.OrderEntity, com.example.tingle.store.entity.QOrderEntity> orderEntities = this.<com.example.tingle.store.entity.OrderEntity, com.example.tingle.store.entity.QOrderEntity>createList("orderEntities", com.example.tingle.store.entity.OrderEntity.class, com.example.tingle.store.entity.QOrderEntity.class, PathInits.DIRECT2);

    public final StringPath password = createString("password");

    public final StringPath picture = createString("picture");

    public final StringPath provider = createString("provider");

    public final EnumPath<com.example.tingle.user.entity.Role> role = createEnum("role", com.example.tingle.user.entity.Role.class);

    public final StringPath username = createString("username");

    public QStarEntity(String variable) {
        super(StarEntity.class, forVariable(variable));
    }

    public QStarEntity(Path<? extends StarEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStarEntity(PathMetadata metadata) {
        super(StarEntity.class, metadata);
    }

}

