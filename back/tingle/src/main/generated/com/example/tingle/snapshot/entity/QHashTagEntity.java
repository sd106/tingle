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

    public static final QHashTagEntity hashTagEntity = new QHashTagEntity("hashTagEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<SnapShotTag, QSnapShotTag> snapShotTags = this.<SnapShotTag, QSnapShotTag>createList("snapShotTags", SnapShotTag.class, QSnapShotTag.class, PathInits.DIRECT2);

    public final StringPath tag = createString("tag");

    public QHashTagEntity(String variable) {
        super(HashTagEntity.class, forVariable(variable));
    }

    public QHashTagEntity(Path<? extends HashTagEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHashTagEntity(PathMetadata metadata) {
        super(HashTagEntity.class, metadata);
    }

}

