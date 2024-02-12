package com.example.tingle.snapshot.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSnapShotTag is a Querydsl query type for SnapShotTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSnapShotTag extends EntityPathBase<SnapShotTag> {

    private static final long serialVersionUID = 1784684255L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSnapShotTag snapShotTag = new QSnapShotTag("snapShotTag");

    public final QHashTagEntity hashTagEntity;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QSnapShotEntity snapShotEntity;

    public QSnapShotTag(String variable) {
        this(SnapShotTag.class, forVariable(variable), INITS);
    }

    public QSnapShotTag(Path<? extends SnapShotTag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSnapShotTag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSnapShotTag(PathMetadata metadata, PathInits inits) {
        this(SnapShotTag.class, metadata, inits);
    }

    public QSnapShotTag(Class<? extends SnapShotTag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.hashTagEntity = inits.isInitialized("hashTagEntity") ? new QHashTagEntity(forProperty("hashTagEntity")) : null;
        this.snapShotEntity = inits.isInitialized("snapShotEntity") ? new QSnapShotEntity(forProperty("snapShotEntity"), inits.get("snapShotEntity")) : null;
    }

}

