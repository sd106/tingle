package com.example.tingle.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QStarEntity is a Querydsl query type for StarEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStarEntity extends EntityPathBase<StarEntity> {

    private static final long serialVersionUID = -2024102605L;

    public static final QStarEntity starEntity = new QStarEntity("starEntity");

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath password = createString("password");

    public final StringPath picture = createString("picture");

    public final StringPath provider = createString("provider");

    public final EnumPath<Role> role = createEnum("role", Role.class);

    public final StringPath username = createString("username");

    public QStarEntity(String variable) {
        super(StarEntity.class, forVariable(variable));
    }

    public QStarEntity(Path<? extends StarEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStarEntity(PathMetadata metadata) {
        super(StarEntity.class, metadata);
    }

}

