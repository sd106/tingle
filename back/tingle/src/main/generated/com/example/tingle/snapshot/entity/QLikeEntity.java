package com.example.tingle.snapshot.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLikeEntity is a Querydsl query type for LikeEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLikeEntity extends EntityPathBase<LikeEntity> {

    private static final long serialVersionUID = 6814065L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLikeEntity likeEntity = new QLikeEntity("likeEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QSnapShotEntity snapShot;

    public final com.example.tingle.star.entity.QStarEntity star;

    public final com.example.tingle.user.entity.QUserEntity user;

    public QLikeEntity(String variable) {
        this(LikeEntity.class, forVariable(variable), INITS);
    }

    public QLikeEntity(Path<? extends LikeEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLikeEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLikeEntity(PathMetadata metadata, PathInits inits) {
        this(LikeEntity.class, metadata, inits);
    }

    public QLikeEntity(Class<? extends LikeEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.snapShot = inits.isInitialized("snapShot") ? new QSnapShotEntity(forProperty("snapShot"), inits.get("snapShot")) : null;
        this.star = inits.isInitialized("star") ? new com.example.tingle.star.entity.QStarEntity(forProperty("star")) : null;
        this.user = inits.isInitialized("user") ? new com.example.tingle.user.entity.QUserEntity(forProperty("user")) : null;
    }

}

