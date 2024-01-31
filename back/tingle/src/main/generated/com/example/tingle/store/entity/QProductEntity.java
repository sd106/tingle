package com.example.tingle.store.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductEntity is a Querydsl query type for ProductEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductEntity extends EntityPathBase<ProductEntity> {

    private static final long serialVersionUID = 590429034L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductEntity productEntity = new QProductEntity("productEntity");

    public final NumberPath<Integer> amount = createNumber("amount", Integer.class);

    public final BooleanPath available = createBoolean("available");

    public final StringPath content = createString("content");

    public final NumberPath<Long> Id = createNumber("Id", Long.class);

    public final ListPath<ProductImageEntity, QProductImageEntity> imageUrl = this.<ProductImageEntity, QProductImageEntity>createList("imageUrl", ProductImageEntity.class, QProductImageEntity.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final ListPath<OrderEntity, QOrderEntity> orders = this.<OrderEntity, QOrderEntity>createList("orders", OrderEntity.class, QOrderEntity.class, PathInits.DIRECT2);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final com.example.tingle.user.entity.QStarEntity starId;

    public final StringPath starName = createString("starName");

    public QProductEntity(String variable) {
        this(ProductEntity.class, forVariable(variable), INITS);
    }

    public QProductEntity(Path<? extends ProductEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductEntity(PathMetadata metadata, PathInits inits) {
        this(ProductEntity.class, metadata, inits);
    }

    public QProductEntity(Class<? extends ProductEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.starId = inits.isInitialized("starId") ? new com.example.tingle.user.entity.QStarEntity(forProperty("starId")) : null;
    }

}

