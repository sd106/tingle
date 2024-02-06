package com.example.tingle.chat.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatRoomEntity is a Querydsl query type for ChatRoomEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatRoomEntity extends EntityPathBase<ChatRoomEntity> {

    private static final long serialVersionUID = -1934432447L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChatRoomEntity chatRoomEntity = new QChatRoomEntity("chatRoomEntity");

    public final ListPath<ChatMessageEntity, QChatMessageEntity> chatMessageList = this.<ChatMessageEntity, QChatMessageEntity>createList("chatMessageList", ChatMessageEntity.class, QChatMessageEntity.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.example.tingle.star.entity.QStarEntity star;

    public QChatRoomEntity(String variable) {
        this(ChatRoomEntity.class, forVariable(variable), INITS);
    }

    public QChatRoomEntity(Path<? extends ChatRoomEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChatRoomEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChatRoomEntity(PathMetadata metadata, PathInits inits) {
        this(ChatRoomEntity.class, metadata, inits);
    }

    public QChatRoomEntity(Class<? extends ChatRoomEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.star = inits.isInitialized("star") ? new com.example.tingle.star.entity.QStarEntity(forProperty("star")) : null;
    }

}

