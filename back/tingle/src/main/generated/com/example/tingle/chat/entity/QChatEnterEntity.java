package com.example.tingle.chat.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatEnterEntity is a Querydsl query type for ChatEnterEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatEnterEntity extends EntityPathBase<ChatEnterEntity> {

    private static final long serialVersionUID = 843203224L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChatEnterEntity chatEnterEntity = new QChatEnterEntity("chatEnterEntity");

    public final com.example.tingle.follow.entity.QFollowEntity follow;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QChatEnterEntity(String variable) {
        this(ChatEnterEntity.class, forVariable(variable), INITS);
    }

    public QChatEnterEntity(Path<? extends ChatEnterEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChatEnterEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChatEnterEntity(PathMetadata metadata, PathInits inits) {
        this(ChatEnterEntity.class, metadata, inits);
    }

    public QChatEnterEntity(Class<? extends ChatEnterEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.follow = inits.isInitialized("follow") ? new com.example.tingle.follow.entity.QFollowEntity(forProperty("follow"), inits.get("follow")) : null;
    }

}

