package com.example.tingle.home.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHomeEntity is a Querydsl query type for HomeEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHomeEntity extends EntityPathBase<HomeEntity> {

    private static final long serialVersionUID = 368255508L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHomeEntity homeEntity = new QHomeEntity("homeEntity");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final ListPath<HomePictureEntity, QHomePictureEntity> homePictureEntities = this.<HomePictureEntity, QHomePictureEntity>createList("homePictureEntities", HomePictureEntity.class, QHomePictureEntity.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> ordering = createNumber("ordering", Long.class);

    public final com.example.tingle.star.entity.QStarEntity starEntity;

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QHomeEntity(String variable) {
        this(HomeEntity.class, forVariable(variable), INITS);
    }

    public QHomeEntity(Path<? extends HomeEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHomeEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHomeEntity(PathMetadata metadata, PathInits inits) {
        this(HomeEntity.class, metadata, inits);
    }

    public QHomeEntity(Class<? extends HomeEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.starEntity = inits.isInitialized("starEntity") ? new com.example.tingle.star.entity.QStarEntity(forProperty("starEntity")) : null;
    }

}

