package com.example.tingle.home.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHomePictureEntity is a Querydsl query type for HomePictureEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHomePictureEntity extends EntityPathBase<HomePictureEntity> {

    private static final long serialVersionUID = -1253077200L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHomePictureEntity homePictureEntity = new QHomePictureEntity("homePictureEntity");

    public final QHomeEntity homeEntity;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath image = createString("image");

    public QHomePictureEntity(String variable) {
        this(HomePictureEntity.class, forVariable(variable), INITS);
    }

    public QHomePictureEntity(Path<? extends HomePictureEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHomePictureEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHomePictureEntity(PathMetadata metadata, PathInits inits) {
        this(HomePictureEntity.class, metadata, inits);
    }

    public QHomePictureEntity(Class<? extends HomePictureEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.homeEntity = inits.isInitialized("homeEntity") ? new QHomeEntity(forProperty("homeEntity"), inits.get("homeEntity")) : null;
    }

}

