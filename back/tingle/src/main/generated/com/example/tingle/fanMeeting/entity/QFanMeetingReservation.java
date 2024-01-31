package com.example.tingle.fanMeeting.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFanMeetingReservation is a Querydsl query type for FanMeetingReservation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFanMeetingReservation extends EntityPathBase<FanMeetingReservation> {

    private static final long serialVersionUID = -1031567959L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFanMeetingReservation fanMeetingReservation = new QFanMeetingReservation("fanMeetingReservation");

    public final QFanMeeting fanMeeting;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> orderAt = createDateTime("orderAt", java.time.LocalDateTime.class);

    public final com.example.tingle.user.entity.QStarEntity star;

    public final com.example.tingle.user.entity.QUserEntity user;

    public QFanMeetingReservation(String variable) {
        this(FanMeetingReservation.class, forVariable(variable), INITS);
    }

    public QFanMeetingReservation(Path<? extends FanMeetingReservation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFanMeetingReservation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFanMeetingReservation(PathMetadata metadata, PathInits inits) {
        this(FanMeetingReservation.class, metadata, inits);
    }

    public QFanMeetingReservation(Class<? extends FanMeetingReservation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.fanMeeting = inits.isInitialized("fanMeeting") ? new QFanMeeting(forProperty("fanMeeting"), inits.get("fanMeeting")) : null;
        this.star = inits.isInitialized("star") ? new com.example.tingle.user.entity.QStarEntity(forProperty("star")) : null;
        this.user = inits.isInitialized("user") ? new com.example.tingle.user.entity.QUserEntity(forProperty("user")) : null;
    }

}

