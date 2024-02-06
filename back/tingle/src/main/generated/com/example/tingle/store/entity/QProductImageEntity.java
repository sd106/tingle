package com.example.tingle.store.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductImageEntity is a Querydsl query type for ProductImageEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductImageEntity extends EntityPathBase<ProductImageEntity> {

    private static final long serialVersionUID = -1195700745L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductImageEntity productImageEntity = new QProductImageEntity("productImageEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QProductEntity product;

    public final StringPath url = createString("url");

    public QProductImageEntity(String variable) {
        this(ProductImageEntity.class, forVariable(variable), INITS);
    }

    public QProductImageEntity(Path<? extends ProductImageEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductImageEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductImageEntity(PathMetadata metadata, PathInits inits) {
        this(ProductImageEntity.class, metadata, inits);
    }

    public QProductImageEntity(Class<? extends ProductImageEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new QProductEntity(forProperty("product"), inits.get("product")) : null;
    }

}

