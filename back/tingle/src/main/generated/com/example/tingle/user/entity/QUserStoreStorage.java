package com.example.tingle.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserStoreStorage is a Querydsl query type for UserStoreStorage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserStoreStorage extends EntityPathBase<UserStoreStorage> {

    private static final long serialVersionUID = 815353763L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserStoreStorage userStoreStorage = new QUserStoreStorage("userStoreStorage");

    public final StringPath Content = createString("Content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.example.tingle.star.entity.QStarEntity star;

    public final NumberPath<Long> storageId = createNumber("storageId", Long.class);

    public final StringPath title = createString("title");

    public final StringPath url = createString("url");

    public final QUserEntity user;

    public QUserStoreStorage(String variable) {
        this(UserStoreStorage.class, forVariable(variable), INITS);
    }

    public QUserStoreStorage(Path<? extends UserStoreStorage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserStoreStorage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserStoreStorage(PathMetadata metadata, PathInits inits) {
        this(UserStoreStorage.class, metadata, inits);
    }

    public QUserStoreStorage(Class<? extends UserStoreStorage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.star = inits.isInitialized("star") ? new com.example.tingle.star.entity.QStarEntity(forProperty("star")) : null;
        this.user = inits.isInitialized("user") ? new QUserEntity(forProperty("user")) : null;
    }

}

