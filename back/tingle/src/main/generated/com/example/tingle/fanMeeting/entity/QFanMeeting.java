package com.example.tingle.fanMeeting.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFanMeeting is a Querydsl query type for FanMeeting
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFanMeeting extends EntityPathBase<FanMeeting> {

    private static final long serialVersionUID = 536330787L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFanMeeting fanMeeting = new QFanMeeting("fanMeeting");

    public final ListPath<FanMeetingType, QFanMeetingType> availableFanMeetingTypes = this.<FanMeetingType, QFanMeetingType>createList("availableFanMeetingTypes", FanMeetingType.class, QFanMeetingType.class, PathInits.DIRECT2);

    public final NumberPath<Integer> capacity = createNumber("capacity", Integer.class);

    public final StringPath description = createString("description");

    public final com.example.tingle.user.entity.QUserEntity fan;

    public final DateTimePath<java.time.LocalDateTime> fanMeetingStartAt = createDateTime("fanMeetingStartAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imgURL = createString("imgURL");

    public final BooleanPath isFinished = createBoolean("isFinished");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final com.example.tingle.star.entity.QStarEntity star;

    public final DateTimePath<java.time.LocalDateTime> ticketingEndAt = createDateTime("ticketingEndAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> ticketingStartAt = createDateTime("ticketingStartAt", java.time.LocalDateTime.class);

    public final StringPath title = createString("title");

    public QFanMeeting(String variable) {
        this(FanMeeting.class, forVariable(variable), INITS);
    }

    public QFanMeeting(Path<? extends FanMeeting> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFanMeeting(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFanMeeting(PathMetadata metadata, PathInits inits) {
        this(FanMeeting.class, metadata, inits);
    }

    public QFanMeeting(Class<? extends FanMeeting> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.fan = inits.isInitialized("fan") ? new com.example.tingle.user.entity.QUserEntity(forProperty("fan")) : null;
        this.star = inits.isInitialized("star") ? new com.example.tingle.star.entity.QStarEntity(forProperty("star")) : null;
    }

}

