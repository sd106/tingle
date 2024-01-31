package com.example.tingle.snapshot.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHashTagEntity is a Querydsl query type for HashTagEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHashTagEntity extends EntityPathBase<HashTagEntity> {

    private static final long serialVersionUID = 1277423672L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHashTagEntity hashTagEntity = new QHashTagEntity("hashTagEntity");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QSnapShotEntity snapshot;

    public final StringPath tag = createString("tag");

    public QHashTagEntity(String variable) {
        this(HashTagEntity.class, forVariable(variable), INITS);
    }

    public QHashTagEntity(Path<? extends HashTagEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHashTagEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHashTagEntity(PathMetadata metadata, PathInits inits) {
        this(HashTagEntity.class, metadata, inits);
    }

    public QHashTagEntity(Class<? extends HashTagEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.snapshot = inits.isInitialized("snapshot") ? new QSnapShotEntity(forProperty("snapshot"), inits.get("snapshot")) : null;
    }

}

