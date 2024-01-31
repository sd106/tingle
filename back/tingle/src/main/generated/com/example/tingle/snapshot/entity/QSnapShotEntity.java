package com.example.tingle.snapshot.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSnapShotEntity is a Querydsl query type for SnapShotEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSnapShotEntity extends EntityPathBase<SnapShotEntity> {

    private static final long serialVersionUID = -288456066L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSnapShotEntity snapShotEntity = new QSnapShotEntity("snapShotEntity");

    public final ListPath<CommentEntity, QCommentEntity> comments = this.<CommentEntity, QCommentEntity>createList("comments", CommentEntity.class, QCommentEntity.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final NumberPath<Integer> likes = createNumber("likes", Integer.class);

    public final ListPath<SnapShotTag, QSnapShotTag> snapShotTags = this.<SnapShotTag, QSnapShotTag>createList("snapShotTags", SnapShotTag.class, QSnapShotTag.class, PathInits.DIRECT2);

    public final ListPath<SnapShotTag, QSnapShotTag> snapShotTags = this.<SnapShotTag, QSnapShotTag>createList("snapShotTags", SnapShotTag.class, QSnapShotTag.class, PathInits.DIRECT2);

    public final com.example.tingle.star.entity.QStarEntity star;

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final com.example.tingle.user.entity.QUserEntity user;

    public QSnapShotEntity(String variable) {
        this(SnapShotEntity.class, forVariable(variable), INITS);
    }

    public QSnapShotEntity(Path<? extends SnapShotEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSnapShotEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSnapShotEntity(PathMetadata metadata, PathInits inits) {
        this(SnapShotEntity.class, metadata, inits);
    }

    public QSnapShotEntity(Class<? extends SnapShotEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.star = inits.isInitialized("star") ? new com.example.tingle.star.entity.QStarEntity(forProperty("star")) : null;
        this.user = inits.isInitialized("user") ? new com.example.tingle.user.entity.QUserEntity(forProperty("user")) : null;
    }

}

