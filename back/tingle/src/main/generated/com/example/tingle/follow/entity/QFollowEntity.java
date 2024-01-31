package com.example.tingle.follow.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFollowEntity is a Querydsl query type for FollowEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFollowEntity extends EntityPathBase<FollowEntity> {

    private static final long serialVersionUID = 497017656L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFollowEntity followEntity = new QFollowEntity("followEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.example.tingle.user.entity.QStarEntity starEntity;

    public final com.example.tingle.user.entity.QUserEntity userEntity;

    public QFollowEntity(String variable) {
        this(FollowEntity.class, forVariable(variable), INITS);
    }

    public QFollowEntity(Path<? extends FollowEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFollowEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFollowEntity(PathMetadata metadata, PathInits inits) {
        this(FollowEntity.class, metadata, inits);
    }

    public QFollowEntity(Class<? extends FollowEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.starEntity = inits.isInitialized("starEntity") ? new com.example.tingle.user.entity.QStarEntity(forProperty("starEntity")) : null;
        this.userEntity = inits.isInitialized("userEntity") ? new com.example.tingle.user.entity.QUserEntity(forProperty("userEntity")) : null;
    }

}

